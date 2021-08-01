package com.stevecinema.economy.command;

import com.stevecinema.economy.EconomyPlugin;
import com.stevecinema.economy.vault.SilverEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private EconomyPlugin economyPlugin;

    public PayCommand(EconomyPlugin economyPlugin) {
        this.economyPlugin = economyPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "/" + label + " <username> <amount>");
            return true;
        }

        Player target = economyPlugin.getServer().getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + ChatColor.RED.toString() + target + " is not online");
            return true;
        }

        if (player.equals(target)) {
            player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + ChatColor.RED + "You cannot pay yourself");
            return true;
        }

        long amount;

        try {
            amount = Long.parseLong(args[1].replace(",", ""));
        } catch (NumberFormatException e) {
            player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + ChatColor.RED + "Invalid amount");
            return true;
        }

        if (amount < 1 || amount > 1_000_000) {
            player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + ChatColor.RED + "Amount must be between 0 and 1,000,000");
            return true;
        }

        if (!economyPlugin.getSilverEconomy().has(player, amount)) {
            player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + ChatColor.RED + "You do not have that much");
            return true;
        }

        boolean success = false;
        SilverEconomy eco = economyPlugin.getSilverEconomy();

        // Take from original player
        if (eco.withdrawPlayer(player, amount).type == EconomyResponse.ResponseType.SUCCESS) {
            // Deposit to target player
            if (eco.depositPlayer(target, amount).type == EconomyResponse.ResponseType.SUCCESS) {
                success = true;
            } else {
                // If could not deposit, give back to original player
                eco.depositPlayer(player, amount);
            }
        }

        if (success) {
            player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + "You have sent " + SilverEconomy.SILVER_COLOR + amount + " silver " + ChatColor.RESET + "to " + target.getName());
            target.sendMessage(EconomyPlugin.MESSAGE_PREFIX + player.getName() + " has sent you " + SilverEconomy.SILVER_COLOR + amount + " silver");
        } else {
            player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + ChatColor.RED + "There was an error while transferring the funds.");
        }

        return true;
    }

}

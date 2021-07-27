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
            player.sendMessage(ChatColor.RED.toString() + target + " is not online");
            return true;
        }

        long amount;

        try {
            amount = Long.parseLong(args[1].replace(",", ""));
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid amount.");
            return true;
        }

        if (amount < 1 || amount > 1_000_000) {
            player.sendMessage(ChatColor.RED + "Amount must be between 0 and 1,000,000.");
            return true;
        }

        if (!economyPlugin.getSilverEconomy().has(target, amount)) {
            player.sendMessage(ChatColor.RED + "You do not have that much.");
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
            player.sendMessage(SilverEconomy.SILVER_COLOR + "You have sent " + amount + " silver to " + target.getName());
            target.sendMessage(SilverEconomy.SILVER_COLOR + " has sent you " + amount + " silver.");
        } else {
            player.sendMessage(ChatColor.RED + "There was an error while transferring the funds.");
        }

        return true;
    }

}

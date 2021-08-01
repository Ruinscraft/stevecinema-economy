package com.stevecinema.economy.command;

import com.stevecinema.economy.EconomyPlugin;
import com.stevecinema.economy.storage.EconomyAccount;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EcoAdminCommand implements CommandExecutor {

    private EconomyPlugin economyPlugin;

    public EcoAdminCommand(EconomyPlugin economyPlugin) {
        this.economyPlugin = economyPlugin;
    }

    /*
     *  /ecoadmin check <username>
     *  /ecoadmin withdraw <username> <amount>
     *  /ecoadmin deposit <username> <amount>
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            showHelp(sender);
            return true;
        }

        String target = args[1];
        OfflinePlayer targetPlayer = economyPlugin.getServer().getOfflinePlayer(target);

        EconomyAccount account = null;

        if (targetPlayer.isOnline()) {
            account = economyPlugin.getAccountManager().getAccount(targetPlayer.getUniqueId());
        } else {
            if (targetPlayer.hasPlayedBefore()) {
                account = economyPlugin.getEconomyStorage().loadAccount(targetPlayer.getUniqueId()).join();
            }
        }

        if (account == null) {
            sender.sendMessage(ChatColor.RED + "Could not load account for " + target + ". Have they joined before?");
            return true;
        }

        long amount = 0;

        if (args.length > 2) {
            try {
                amount = Long.parseLong(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid amount.");
            }
        }

        switch (args[0].toLowerCase()) {
            case "check" -> check(sender, account);
            case "withdraw" -> withdraw(sender, account, amount);
            case "deposit" -> deposit(sender, account, amount);
            default -> showHelp(sender);
        }

        return true;
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "==== EcoAdmin commands ====");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ecoadmin check <username>");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ecoadmin withdraw <username> <amount>");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ecoadmin deposit <username> <amount>");
    }

    private void check(CommandSender sender, EconomyAccount account) {
        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + account.getHolder() + " has " + account.getBalance());
    }

    private void withdraw(CommandSender sender, EconomyAccount account, long amount) {
        account.withdraw(amount);
        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + amount + " has been withdrawn from " + account.getHolder() + ". New balance: " + account.getBalance());
    }

    private void deposit(CommandSender sender, EconomyAccount account, long amount) {
        account.deposit(amount);
        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + amount + " has been deposited to " + account.getHolder() + ". New balance: " + account.getBalance());
    }

}

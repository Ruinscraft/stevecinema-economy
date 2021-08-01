package com.stevecinema.economy.command;

import com.stevecinema.economy.EconomyPlugin;
import com.stevecinema.economy.vault.SilverEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private EconomyPlugin economyPlugin;

    public BalanceCommand(EconomyPlugin economyPlugin) {
        this.economyPlugin = economyPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;
        SilverEconomy eco = economyPlugin.getSilverEconomy();
        player.sendMessage(EconomyPlugin.MESSAGE_PREFIX + "You have " + SilverEconomy.SILVER_COLOR + (long) eco.getBalance(player) + " " + eco.currencyNamePlural());
        return true;
    }

}

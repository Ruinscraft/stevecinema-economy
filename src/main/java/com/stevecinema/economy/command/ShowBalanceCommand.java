package com.stevecinema.economy.command;

import com.stevecinema.economy.EconomyPlugin;
import com.stevecinema.economy.vault.SilverEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowBalanceCommand implements CommandExecutor {

    private EconomyPlugin economyPlugin;

    public ShowBalanceCommand(EconomyPlugin economyPlugin) {
        this.economyPlugin = economyPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return false;
        SilverEconomy eco = economyPlugin.getSilverEconomy();
        player.sendMessage(SilverEconomy.SILVER_COLOR + player.getName() + " has " + (long) eco.getBalance(player) + " silver.");
        return true;
    }

}

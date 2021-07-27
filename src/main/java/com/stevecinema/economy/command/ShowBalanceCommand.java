package com.stevecinema.economy.command;

import com.stevecinema.economy.EconomyPlugin;
import com.stevecinema.economy.vault.SilverEconomy;
import net.md_5.bungee.api.ChatColor;
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
        if (!(sender instanceof Player player)) {
            return false;
        }

        SilverEconomy eco = economyPlugin.getSilverEconomy();
        long balance = (long) eco.getBalance(player);

        player.sendMessage(ChatColor.of("#C0C0C0") + "");

        return true;
    }

}

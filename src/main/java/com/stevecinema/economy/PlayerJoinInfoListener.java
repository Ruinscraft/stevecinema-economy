package com.stevecinema.economy;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinInfoListener implements Listener {

    private EconomyPlugin economyPlugin;

    public PlayerJoinInfoListener(EconomyPlugin economyPlugin) {
        this.economyPlugin = economyPlugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        economyPlugin.getServer().getScheduler().runTaskLater(economyPlugin, () -> {
            if (player.isOnline()) {
                long balance = (long) economyPlugin.getSilverEconomy().getBalance(player);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText("You have " + balance + " " + economyPlugin.getSilverEconomy().currencyNamePlural() + ChatColor.RESET +  " to spend!"));
            }
        }, 20L);
    }

}

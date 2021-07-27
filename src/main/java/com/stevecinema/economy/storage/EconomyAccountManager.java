package com.stevecinema.economy.storage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EconomyAccountManager implements Listener {

    private EconomyStorage economyStorage;
    private Map<UUID, EconomyAccount> loadedAccounts;

    public EconomyAccountManager(EconomyStorage economyStorage) {
        this.economyStorage = economyStorage;
        loadedAccounts = new ConcurrentHashMap<>();
    }

    public boolean isLoaded(UUID uuid) {
        return loadedAccounts.containsKey(uuid);
    }

    public EconomyAccount getAccount(UUID uuid) {
        return loadedAccounts.get(uuid);
    }

    public void load(Player player) {
        economyStorage.loadAccount(player.getUniqueId()).thenAccept(economyAccount -> {
            if (player.isOnline()) { // in case they log out before the account was loaded
                loadedAccounts.put(player.getUniqueId(), economyAccount);
            }
        });
    }

    public void unload(Player player) {
        loadedAccounts.remove(player.getUniqueId());
    }

    public void unloadAll() {
        loadedAccounts.clear();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        load(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        unload(event.getPlayer());
    }

}

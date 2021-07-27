package com.stevecinema.economy.vault;

import com.stevecinema.economy.EconomyPlugin;
import com.stevecinema.economy.storage.EconomyAccount;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.EconomyResponse;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.OfflinePlayer;

public class SilverEconomy extends NoBankEconomy {

    public static final ChatColor SILVER_COLOR = ChatColor.of("#C0C0C0");

    private final EconomyPlugin economyPlugin;

    public SilverEconomy(EconomyPlugin economyPlugin) {
        this.economyPlugin = economyPlugin;
    }

    @Override
    public boolean isEnabled() {
        return economyPlugin.isEnabled();
    }

    @Override
    public String getName() {
        return economyPlugin.getDescription().getName();
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return amount + " " + currencyNamePlural();
    }

    @Override
    public String currencyNamePlural() {
        return "silver";
    }

    @Override
    public String currencyNameSingular() {
        return "silver";
    }

    @Override
    @Deprecated
    public boolean hasAccount(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return economyPlugin.getAccountManager().isLoaded(offlinePlayer.getUniqueId());
    }

    @Override
    @Deprecated
    public boolean hasAccount(String playerName, String worldName) {
        throw new NotImplementedException();
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String worldName) {
        return hasAccount(offlinePlayer);
    }

    @Override
    @Deprecated
    public double getBalance(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        EconomyAccount account = economyPlugin.getAccountManager().getAccount(offlinePlayer.getUniqueId());
        return account == null ? 0 : account.getBalance();
    }

    @Override
    @Deprecated
    public double getBalance(String playerName, String worldName) {
        throw new NotImplementedException();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String worldName) {
        return getBalance(offlinePlayer);
    }

    @Override
    @Deprecated
    public boolean has(String playerName, double amount) {
        throw new NotImplementedException();
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        EconomyAccount account = economyPlugin.getAccountManager().getAccount(offlinePlayer.getUniqueId());
        return account != null && account.has((long) amount);
    }

    @Override
    @Deprecated
    public boolean has(String playerName, String worldName, double amount) {
        throw new NotImplementedException();
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return has(offlinePlayer, amount);
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (amount < 1)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Amount cannot be negative");
        if (amount > 1_000_000)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Amount cannot be larger than 1 million");
        EconomyAccount account = economyPlugin.getAccountManager().getAccount(offlinePlayer.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Account not loaded");
        if (!account.has((long) amount))
            return new EconomyResponse(0, getBalance(offlinePlayer), EconomyResponse.ResponseType.FAILURE, "Does not have enough");
        account.withdraw((long) amount);
        return new EconomyResponse(amount, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return withdrawPlayer(offlinePlayer, amount);
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String playerName, double amount) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (amount < 1)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Amount cannot be negative");
        if (amount > 1_000_000)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Amount cannot be larger than 1 million");
        EconomyAccount account = economyPlugin.getAccountManager().getAccount(offlinePlayer.getUniqueId());
        if (account == null)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Account not loaded");
        account.deposit((long) amount);
        return new EconomyResponse(amount, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return depositPlayer(offlinePlayer, amount);
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        throw new NotImplementedException();
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String worldName) {
        return true;
    }

}

package com.stevecinema.economy.vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.OfflinePlayer;

import java.util.List;

public abstract class NoBankEconomy implements Economy {

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    @Deprecated
    public EconomyResponse createBank(String name, String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankHas(String name, double balance) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double balance) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse bankDeposit(String name, double balance) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getBanks() {
        throw new NotImplementedException();
    }

}

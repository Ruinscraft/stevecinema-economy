package com.stevecinema.economy.storage.sql;

import com.stevecinema.economy.storage.EconomyAccount;
import com.stevecinema.economy.storage.EconomyStorage;

import java.util.UUID;

public class RelationalEconomyAccount extends EconomyAccount {

    private int relId;

    public RelationalEconomyAccount(EconomyStorage economyStorage, UUID holder, long balance, long lastUpdated, int relId) {
        super(economyStorage, holder, balance, lastUpdated);
        this.relId = relId;
    }

    public int getRelId() {
        return relId;
    }

    public void setRelId(int relId) {
        this.relId = relId;
    }

}

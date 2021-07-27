package com.stevecinema.economy.storage.sql;

import com.stevecinema.economy.storage.EconomyAccount;
import com.stevecinema.economy.storage.EconomyStorage;

import java.util.UUID;

public class RelationalEconomyAccount extends EconomyAccount {

    private final int relId;

    public RelationalEconomyAccount(EconomyStorage economyStorage, UUID holder, long balance, int relId) {
        super(economyStorage, holder, balance);
        this.relId = relId;
    }

    public int getRelId() {
        return relId;
    }

}

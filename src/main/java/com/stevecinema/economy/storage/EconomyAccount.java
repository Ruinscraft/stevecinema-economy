package com.stevecinema.economy.storage;

import java.util.UUID;

public class EconomyAccount {

    private EconomyStorage economyStorage;
    private final UUID holder;
    private long balance;

    public EconomyAccount(EconomyStorage economyStorage, UUID holder, long balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public UUID getHolder() {
        return holder;
    }

    public long getBalance() {
        return balance;
    }

    public synchronized void deposit(long amount) {
        assert amount > 0;
        economyStorage.saveAccount(this);
    }

    public synchronized void withdraw(long amount) {
        assert has(amount);
        economyStorage.saveAccount(this);
    }

    public synchronized boolean has(long amount) {
        assert amount > 0;
        return balance >= amount;
    }

}

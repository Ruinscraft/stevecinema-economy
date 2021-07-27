package com.stevecinema.economy.storage;

public class EconomyLogEntry {

    private EconomyAccount account;
    private Action action;
    private long amount;
    private long timestamp;

    public EconomyLogEntry(EconomyAccount account, Action action, long amount, long timestamp) {
        this.account = account;
        this.action = action;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public EconomyAccount getAccount() {
        return account;
    }

    public Action getAction() {
        return action;
    }

    public long getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public enum Action {
        W, D
    }

}

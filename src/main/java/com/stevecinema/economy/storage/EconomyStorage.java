package com.stevecinema.economy.storage;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class EconomyStorage {

    public abstract CompletableFuture<EconomyAccount> loadAccount(UUID holder);

    public abstract CompletableFuture<Void> saveAccount(EconomyAccount account);

    public abstract CompletableFuture<Void> saveLogEntry(EconomyLogEntry logEntry);

    public abstract void close();

}

package com.stevecinema.economy.storage.sql;

import com.stevecinema.economy.storage.EconomyAccount;
import com.stevecinema.economy.storage.EconomyStorage;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class SQLEconomyStorage extends EconomyStorage {

    @Override
    public CompletableFuture<EconomyAccount> loadAccount(UUID holder) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection()) {
                return queryAccount(holder, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return new EconomyAccount(this, holder, 0L);
        });
    }

    @Override
    public CompletableFuture<Void> saveAccount(EconomyAccount account) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                if (account instanceof RelationalEconomyAccount relationalAccount) {
                    updateAccount(relationalAccount, connection);
                } else {
                    insertAccount(account, connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    protected abstract Connection getConnection() throws SQLException;

    @Nullable
    protected abstract RelationalEconomyAccount queryAccount(UUID holder, Connection connection) throws SQLException;

    protected abstract int insertAccount(EconomyAccount account, Connection connection) throws SQLException;

    protected abstract void updateAccount(RelationalEconomyAccount account, Connection connection) throws SQLException;

}

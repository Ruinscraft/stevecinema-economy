package com.stevecinema.economy.storage.sql;

import com.stevecinema.economy.storage.EconomyAccount;
import com.stevecinema.economy.storage.EconomyLogEntry;
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
                RelationalEconomyAccount query = queryAccount(holder, connection);

                if (query == null) {
                    RelationalEconomyAccount relationalAccount = new RelationalEconomyAccount(this, holder, 0L, System.currentTimeMillis(), 0);
                    int relId = insertAccount(relationalAccount, connection);
                    relationalAccount.setRelId(relId);
                    return relationalAccount;
                } else {
                    return query;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return new EconomyAccount(this, holder, 0L, System.currentTimeMillis());
        });
    }

    @Override
    public CompletableFuture<Void> saveAccount(EconomyAccount account) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                if (account instanceof RelationalEconomyAccount relationalAccount) {
                    updateAccount(relationalAccount, connection);
                } else {
                    System.out.println("Could not save economy account: " + account.getHolder());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<Void> saveLogEntry(EconomyLogEntry logEntry) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                if (logEntry.getAccount() instanceof RelationalEconomyAccount relationalAccount) {
                    insertLogEntry(relationalAccount, logEntry, connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    protected abstract Connection getConnection() throws SQLException;

    protected abstract void createTables(Connection connection) throws SQLException;

    @Nullable
    protected abstract RelationalEconomyAccount queryAccount(UUID holder, Connection connection) throws SQLException;

    protected abstract int insertAccount(EconomyAccount account, Connection connection) throws SQLException;

    protected abstract void updateAccount(RelationalEconomyAccount account, Connection connection) throws SQLException;

    protected abstract void insertLogEntry(RelationalEconomyAccount account, EconomyLogEntry logEntry, Connection connection) throws SQLException;

}

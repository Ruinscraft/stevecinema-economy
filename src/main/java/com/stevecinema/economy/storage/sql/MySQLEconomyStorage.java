package com.stevecinema.economy.storage.sql;

import com.stevecinema.economy.storage.EconomyAccount;
import com.stevecinema.economy.storage.EconomyLogEntry;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.UUID;

public abstract class MySQLEconomyStorage extends SQLEconomyStorage {

    @Override
    protected void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS economy_accounts (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "holder VARCHAR(36) NOT NULL, " +
                    "balance BIGINT UNSIGNED NOT NULL, " +
                    "last_updated BIGINT UNSIGNED NOT NULL, " +
                    "UNIQUE (holder), " +
                    "PRIMARY KEY (id));");
            statement.execute("CREATE TABLE IF NOT EXISTS economy_log (" +
                    "account_id INT NOT NULL, " +
                    "action CHAR(1) NOT NULL, " +
                    "amount BIGINT NOT NULL, " +
                    "timestamp BIGINT NOT NULL, " +
                    "FOREIGN KEY (account_id) REFERENCES economy_accounts(id));");
        }
    }

    @Override
    protected @Nullable RelationalEconomyAccount queryAccount(UUID holder, Connection connection) throws SQLException {
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM economy_accounts WHERE holder = ?;")) {
            query.setString(1, holder.toString());

            try (ResultSet resultSet = query.executeQuery()) {
                if (resultSet.next()) {
                    int relId = resultSet.getInt("id");
                    long balance = resultSet.getLong("balance");
                    long lastUpdated = resultSet.getLong("last_updated");
                    return new RelationalEconomyAccount(this, holder, balance, lastUpdated, relId);
                }
            }
        }

        return null;
    }

    @Override
    protected int insertAccount(EconomyAccount account, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement("INSERT INTO economy_accounts (holder, balance, last_updated) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            insert.setString(1, account.getHolder().toString());
            insert.setLong(2, account.getBalance());
            insert.setLong(3, account.getLastUpdated());
            insert.execute();

            try (ResultSet resultSet = insert.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }

        return 0;
    }

    @Override
    protected void updateAccount(RelationalEconomyAccount account, Connection connection) throws SQLException {
        assert account.getRelId() > 0;
        try (PreparedStatement update = connection.prepareStatement("UPDATE economy_accounts SET balance = ?, last_updated = ? WHERE id = ?;")) {
            update.setLong(1, account.getBalance());
            update.setLong(2, account.getLastUpdated());
            update.setInt(3, account.getRelId());
            update.execute();
        }
    }

    @Override
    protected void insertLogEntry(RelationalEconomyAccount account, EconomyLogEntry logEntry, Connection connection) throws SQLException {
        assert account.getRelId() > 0;
        try (PreparedStatement insert = connection.prepareStatement("INSERT INTO economy_log (account_id, action, amount, timestamp) VALUES (?, ?, ?, ?);")) {
            insert.setInt(1, account.getRelId());
            insert.setString(2, logEntry.getAction().name());
            insert.setLong(3, logEntry.getAmount());
            insert.setLong(4, logEntry.getTimestamp());
            insert.execute();
        }
    }

}

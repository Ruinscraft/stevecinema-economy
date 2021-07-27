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
                    "FOREIGN KEY account_id REFERENCES economy_accounts (id);");
        }
    }

    @Override
    protected @Nullable RelationalEconomyAccount queryAccount(UUID holder, Connection connection) throws SQLException {
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM economy_accounts WHERE holder = ?;")) {
            query.setString(1, holder.toString());

            try (ResultSet resultSet = query.executeQuery()) {
                if (resultSet.next()) {

                }
            }
        }

        return null;
    }

    @Override
    protected int insertAccount(EconomyAccount account, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement("INSERT INTO economy_accounts () VALUES ();")) {

        }

        return 0;
    }

    @Override
    protected void updateAccount(RelationalEconomyAccount account, Connection connection) throws SQLException {
        try (PreparedStatement update = connection.prepareStatement("")) {

        }
    }

    @Override
    protected void insertLogEntry(RelationalEconomyAccount account, EconomyLogEntry logEntry, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement("")) {

        }
    }

}

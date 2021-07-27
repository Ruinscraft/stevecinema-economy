package com.stevecinema.economy.storage.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PooledMySQLEconomyStorage extends MySQLEconomyStorage {

    private HikariConfig config;
    private HikariDataSource dataSource;

    public PooledMySQLEconomyStorage(String host, int port, String database, String username, String password) {
        super(host, port, database, username, password);

        config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName("stevecinema-economy-pool");
        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

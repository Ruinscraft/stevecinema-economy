package com.stevecinema.economy;

import com.stevecinema.economy.command.BalanceCommand;
import com.stevecinema.economy.command.EcoAdminCommand;
import com.stevecinema.economy.command.PayCommand;
import com.stevecinema.economy.command.ShowBalanceCommand;
import com.stevecinema.economy.storage.EconomyAccountManager;
import com.stevecinema.economy.storage.EconomyStorage;
import com.stevecinema.economy.storage.sql.PooledMySQLEconomyStorage;
import com.stevecinema.economy.vault.SilverEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlugin extends JavaPlugin {

    private EconomyStorage economyStorage;
    private EconomyAccountManager accountManager;
    private SilverEconomy silverEconomy;

    public EconomyStorage getEconomyStorage() {
        return economyStorage;
    }

    public EconomyAccountManager getAccountManager() {
        return accountManager;
    }

    public SilverEconomy getSilverEconomy() {
        return silverEconomy;
    }

    @Override
    public void onEnable() {
        String mysqlHost = getConfig().getString("storage.mysql.host");
        int mysqlPort = getConfig().getInt("storage.mysql.port");
        String mysqlDatabase = getConfig().getString("storage.mysql.database");
        String mysqlUsername = getConfig().getString("storage.mysql.username");
        String mysqlPassword = getConfig().getString("storage.mysql.password");
        economyStorage = new PooledMySQLEconomyStorage(mysqlHost, mysqlPort, mysqlDatabase, mysqlUsername, mysqlPassword);
        accountManager = new EconomyAccountManager(economyStorage);
        silverEconomy = new SilverEconomy(this);

        getServer().getServicesManager().register(Economy.class, silverEconomy, this, ServicePriority.Highest);
        getServer().getPluginManager().registerEvents(accountManager, this);

        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("showbalance").setExecutor(new ShowBalanceCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("ecoadmin").setExecutor(new EcoAdminCommand());

        for (Player player : getServer().getOnlinePlayers()) {
            accountManager.load(player);
        }
    }

    @Override
    public void onDisable() {
        if (economyStorage != null) {
            economyStorage.close();
        }

        accountManager.unloadAll();
    }

}

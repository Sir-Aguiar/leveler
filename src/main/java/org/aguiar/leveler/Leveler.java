package org.aguiar.leveler;


import org.aguiar.leveler.commands.*;
import org.aguiar.leveler.database.Database;
import org.aguiar.leveler.listeners.*;
import org.aguiar.leveler.utils.ConfigurationsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public final class Leveler extends JavaPlugin {
  public final File databaseFile = new File(getDataFolder(), "leveler.db");
  public Database database;

  @Override
  public void onEnable() {
    getDataFolder().mkdirs();

    getConfig().options().copyDefaults();

    saveDefaultConfig();

    startDatabase();

    ConfigurationsManager.loadSources(this);

    registerListeners();
    registerCommands();

    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Enabled Successfully", getName().toUpperCase()));
  }

  @Override
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Disabled Successfully", getName().toUpperCase()));
  }

  public void registerListeners() {
    getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
    getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
    getServer().getPluginManager().registerEvents(new LevelUp(this), this);
    getServer().getPluginManager().registerEvents(new EntityDeath(this), this);
    getServer().getPluginManager().registerEvents(new SpawnSetListener(this), this);
    getServer().getPluginManager().registerEvents(new DefineBarriersListener(this), this);
  }

  public void registerCommands() {
    Objects.requireNonNull(this.getCommand("start-raid")).setExecutor(new StartRaid(this));
    Objects.requireNonNull(this.getCommand("leveler-stats")).setExecutor(new PlayerReport(this));
    Objects.requireNonNull(this.getCommand("killer-bone")).setExecutor(new GetEntityKiller());
    Objects.requireNonNull(this.getCommand("new-world")).setExecutor(new NewWorld(this));
    Objects.requireNonNull(this.getCommand("define-barriers")).setExecutor(new DefineBarriers(this));
  }

  public void startDatabase() {
    if (!databaseFile.exists()) {
      try {
        databaseFile.createNewFile();
      } catch (IOException e) {
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Could not create database file", getName().toUpperCase()));

        e.printStackTrace();

        getServer().getPluginManager().disablePlugin(this);

        return;
      }
    }

    try {
      database = new Database(databaseFile.getAbsolutePath());

      Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Database is online", getName().toUpperCase()));
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Could not connect to database", getName().toUpperCase()));

      e.printStackTrace();

      getServer().getPluginManager().disablePlugin(this);
    }
  }
}

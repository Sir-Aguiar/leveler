package org.aguiar.leveler;


import org.aguiar.leveler.commands.StartRaid;
import org.aguiar.leveler.database.Database;
import org.aguiar.leveler.listeners.LevelUpListener;
import org.aguiar.leveler.listeners.PlayerJoin;
import org.aguiar.leveler.listeners.RaidZombieDeathListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.sql.SQLException;

public final class Leveler extends JavaPlugin {
  public Database database;
  public final File databaseFile = new File(getDataFolder(), "leveler.db");
  private final String PLUGIN_NAME = getName();

  @Override
  public void onEnable() {
    getDataFolder().mkdirs();

    if (!databaseFile.exists()) {
      try {
        databaseFile.createNewFile();
      } catch (IOException e) {
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Could not create database file", PLUGIN_NAME.toUpperCase()));
        e.printStackTrace();
        getServer().getPluginManager().disablePlugin(this);
        return;
      }
    }

    try {
      database = new Database(databaseFile.getAbsolutePath());
      Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Database is online", PLUGIN_NAME.toUpperCase()));
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Could not connect to database", PLUGIN_NAME.toUpperCase()));
      e.printStackTrace();
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    this.getCommand("start-raid").setExecutor(new StartRaid(this));

    getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
    getServer().getPluginManager().registerEvents(new LevelUpListener(), this);

    getServer().getPluginManager().registerEvents(new RaidZombieDeathListener(this), this);


    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Enabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

  @Override
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Disabled Successfully", PLUGIN_NAME.toUpperCase()));
  }
}

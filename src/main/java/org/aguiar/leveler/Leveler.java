package org.aguiar.leveler;


import org.aguiar.leveler.commands.GetEntityKiller;
import org.aguiar.leveler.commands.NewWorld;
import org.aguiar.leveler.commands.PlayerReport;
import org.aguiar.leveler.commands.StartRaid;
import org.aguiar.leveler.database.Database;
import org.aguiar.leveler.listeners.LevelUpListener;
import org.aguiar.leveler.listeners.PlayerJoin;
import org.aguiar.leveler.listeners.PlayerLeave;
import org.aguiar.leveler.listeners.RaidZombieDeathListener;
import org.aguiar.leveler.utils.SchematicsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class Leveler extends JavaPlugin {
  public final File databaseFile = new File(getDataFolder(), "leveler.db");
  public Database database;

  @Override
  public void onEnable() {
    getDataFolder().mkdirs();

    startDatabase();

    SchematicsManager.loadSchematicsFromResource(this);

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
    getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
    getServer().getPluginManager().registerEvents(new LevelUpListener(this), this);
    getServer().getPluginManager().registerEvents(new RaidZombieDeathListener(this), this);
  }

  public void registerCommands() {
    this.getCommand("start-raid").setExecutor(new StartRaid(this));
    this.getCommand("leveler-stats").setExecutor(new PlayerReport(this));
    this.getCommand("killer-bone").setExecutor(new GetEntityKiller());
    this.getCommand("new-world").setExecutor(new NewWorld(this));
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

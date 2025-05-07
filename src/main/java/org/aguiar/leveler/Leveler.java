package org.aguiar.leveler;


import org.aguiar.leveler.commands.GetEntityKiller;
import org.aguiar.leveler.commands.NewWorld;
import org.aguiar.leveler.commands.PlayerReport;
import org.aguiar.leveler.commands.StartRaid;
import org.aguiar.leveler.database.Database;
import org.aguiar.leveler.listeners.LevelUpListener;
import org.aguiar.leveler.listeners.PlayerJoin;
import org.aguiar.leveler.listeners.RaidZombieDeathListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class Leveler extends JavaPlugin {
  public final File databaseFile = new File(getDataFolder(), "leveler.db");
  private final String PLUGIN_NAME = getName();
  public Database database;

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

    String schematicResourcePath = "schematics/lvl_1_dungeon.schem";
    File schematicFile = new File(getDataFolder(), schematicResourcePath);

    if (!schematicFile.exists()) {
      getLogger().info("Extraindo schematic padrão: " + schematicResourcePath);
      saveResource(schematicResourcePath, false);
    } else {
      getLogger().info("Schematic já existe na pasta de dados: " + schematicResourcePath);
    }

    this.getCommand("start-raid").setExecutor(new StartRaid(this));
    this.getCommand("leveler-stats").setExecutor(new PlayerReport(this));
    this.getCommand("killer-bone").setExecutor(new GetEntityKiller());
    this.getCommand("new-world").setExecutor(new NewWorld(this));

    getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
    getServer().getPluginManager().registerEvents(new LevelUpListener(this), this);

    getServer().getPluginManager().registerEvents(new RaidZombieDeathListener(this), this);


    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Enabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

  @Override
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Disabled Successfully", PLUGIN_NAME.toUpperCase()));
  }
}

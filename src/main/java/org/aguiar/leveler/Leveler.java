package org.aguiar.leveler;

import org.aguiar.leveler.commands.StartRaid;
import org.aguiar.leveler.events.RaidZombieDeathListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class Leveler extends JavaPlugin {
  private final String PLUGIN_NAME = getName();

  @Override
  public void onEnable() {
    this.saveDefaultConfig();

    this.getCommand("start-raid").setExecutor(new StartRaid(this));
    getServer().getPluginManager().registerEvents(new RaidZombieDeathListener(this), this);

    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Enabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

  @Override
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Disabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

}

package org.aguiar.leveler.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class DungeonConfiguration {
  private final Plugin plugin;
  private final String dungeonId;
  private File configFile;
  private FileConfiguration config;

  public DungeonConfiguration(Plugin plugin, String dungeonId) {
    this.plugin = plugin;
    this.dungeonId = dungeonId;
    this.loadConfig();
  }

  public void loadConfig() {
    File dungeonFolder = new File(plugin.getDataFolder(), "dungeons");

    if (!dungeonFolder.exists()) {
      dungeonFolder.mkdirs();
    }

    configFile = new File(dungeonFolder, dungeonId + ".yml");

    if (!configFile.exists()) {
      try {
        configFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    config = YamlConfiguration.loadConfiguration(configFile);
  }

  public FileConfiguration getConfig() {
    if (config == null) {
      loadConfig();
    }

    return config;
  }

  public void saveConfig() {
    if (config == null || configFile == null) {
      return;
    }

    try {
      getConfig().save(configFile);
    } catch (IOException ex) {
      plugin.getLogger().severe("Não foi possível salvar a configuração da dungeon " + dungeonId);
      ex.printStackTrace();
    }
  }
}

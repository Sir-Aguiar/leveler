package org.aguiar.leveler.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class DungeonConfiguration {
  private final Plugin plugin;
  private final String dungeonId;
  private File configFile;
  private FileConfiguration config;

  public DungeonConfiguration(Plugin plugin, String dungeonId) {
    this.plugin = plugin;
    this.dungeonId = dungeonId;
  }

  public boolean loadConfig(String fileName) {
    File dungeonFolder = new File(plugin.getDataFolder(), "dungeons" + File.separator + dungeonId);
    configFile = new File(dungeonFolder, fileName + ".yml");

    if (!dungeonFolder.exists()) {
      dungeonFolder.mkdirs();
    }

    if (!configFile.exists()) {
      try {
        configFile.createNewFile();
      } catch (IOException e) {
        plugin.getLogger().log(Level.SEVERE, "Não foi possível criar o arquivo de configuração da dungeon: " + dungeonId + ".yml", e);
        return false;
      }
    }

    try {
      config = YamlConfiguration.loadConfiguration(configFile);
      return true;
    } catch (Exception e) {
      plugin.getLogger().log(Level.SEVERE, "Erro ao carregar a configuração da dungeon: " + dungeonId + ".yml. O arquivo pode estar corrompido ou inacessível.", e);
      this.config = null;
      return false;
    }
  }

  public FileConfiguration getConfig() {
    if (config == null) {
      throw new IllegalStateException("A configuração da dungeon '" + dungeonId + "' não foi carregada ou está corrompida.");
    }

    return config;
  }

  public void saveConfig() {
    if (config == null || configFile == null) {
      plugin.getLogger().warning("Não foi possível salvar a configuração da dungeon " + dungeonId + ": Configuração ou arquivo não inicializado. Chame .load() primeiro e verifique o retorno.");
      return;
    }

    try {
      getConfig().save(configFile);
    } catch (IOException ex) {
      plugin.getLogger().log(Level.SEVERE, "Não foi possível salvar a configuração da dungeon " + dungeonId, ex);
    }
  }
}

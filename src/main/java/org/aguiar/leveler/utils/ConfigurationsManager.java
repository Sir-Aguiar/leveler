package org.aguiar.leveler.utils;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class ConfigurationsManager {
  private static final String SCHEMATICS_FOLDER = "schematics" + File.separator;
  private static final String DUNGEONS_FOLDER = "dungeons" + File.separator;

  public static void loadSources(Plugin plugin) {
    List<String> DUNGEONS = plugin.getConfig().getStringList("dungeons");

    DUNGEONS.forEach(dungeon -> {
      String schematicResourcePath = SCHEMATICS_FOLDER + dungeon + ".schem";
      String dungeonConfigPath = DUNGEONS_FOLDER + dungeon + File.separator + "config.yml";
      String dungeonLevelsPath = DUNGEONS_FOLDER + dungeon + File.separator + "levels.yml";

      File configFile = new File(plugin.getDataFolder(), dungeonConfigPath);
      File levelsFile = new File(plugin.getDataFolder(), dungeonLevelsPath);
      File schematicFile = new File(plugin.getDataFolder(), schematicResourcePath);

      if (!schematicFile.exists()) {
        plugin.getLogger().info("Extraindo schematic padrão: " + schematicResourcePath);
        plugin.saveResource(schematicResourcePath, false);
      } else {
        plugin.getLogger().info("Schematic já existe na pasta de dados: " + schematicResourcePath);
      }

      if (!configFile.exists()) {
        plugin.getLogger().info("Extraindo configurações padrões: " + configFile);
        plugin.saveResource(dungeonConfigPath, false);
      } else {
        plugin.getLogger().info("Configurações já existem na pasta de dados: " + dungeonConfigPath);
      }

      if (!levelsFile.exists()) {
        plugin.getLogger().info("Extraindo níveis padrões: " + levelsFile);
        plugin.saveResource(dungeonLevelsPath, false);
      } else {
        plugin.getLogger().info("Níveis já existem na pasta de dados: " + dungeonLevelsPath);
      }
    });
  }
}

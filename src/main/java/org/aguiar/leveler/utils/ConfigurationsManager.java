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
      String dungeonResourcePath = DUNGEONS_FOLDER + dungeon + ".yml";

      File dungeonFile = new File(plugin.getDataFolder(), dungeonResourcePath);
      File schematicFile = new File(plugin.getDataFolder(), schematicResourcePath);

      if (!schematicFile.exists()) {
        plugin.getLogger().info("Extraindo schematic padrão: " + schematicResourcePath);
        plugin.saveResource(schematicResourcePath, false);
      } else {
        plugin.getLogger().info("Schematic já existe na pasta de dados: " + schematicResourcePath);
      }

      if (!dungeonFile.exists()) {
        plugin.getLogger().info("Extraindo configurações padrões: " + dungeonFile);
        plugin.saveResource(dungeonResourcePath, false);
      } else {
        plugin.getLogger().info("Configurações já existem na pasta de dados: " + dungeonResourcePath);
      }
    });
  }
}

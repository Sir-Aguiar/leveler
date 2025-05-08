package org.aguiar.leveler.utils;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SchematicsManager {
  private static final String SCHEMATICS_FOLDER = "schematics/";
  private static final String OWL_DUNGEON_NAME = "owl_dungeon.schem";
  private static final List<String> DUNGEONS = new ArrayList<>(List.of(OWL_DUNGEON_NAME));

  public static void loadSchematicsFromResource(Plugin plugin) {
    DUNGEONS.forEach(dungeon -> {
      String schematicResourcePath = SCHEMATICS_FOLDER + dungeon;
      File schematicFile = new File(plugin.getDataFolder(), schematicResourcePath);

      if (!schematicFile.exists()) {
        plugin.getLogger().info("Extraindo schematic padrão: " + schematicResourcePath);
        plugin.saveResource(schematicResourcePath, false);
      } else {
        plugin.getLogger().info("Schematic já existe na pasta de dados: " + schematicResourcePath);
      }
    });
  }
}

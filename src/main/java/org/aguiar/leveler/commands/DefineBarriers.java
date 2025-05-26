package org.aguiar.leveler.commands;

import org.aguiar.leveler.Leveler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DefineBarriers implements CommandExecutor {
  private final Leveler plugin;

  public DefineBarriers(Leveler plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      return false;
    }

    if (args.length < 1) return false;

    Optional<String> dungeonId = player.getWorld().getMetadata("dungeonId").stream().findFirst().map(MetadataValue::asString);

    if (dungeonId.isEmpty()) {
      player.sendMessage(ChatColor.RED + "Você não está no mundo de uma dungeon");
      return false;
    }

    if (args[0].equalsIgnoreCase("save")) {
      player.removeMetadata("_edit_dungeonId", plugin);
      player.removeMetadata("_edit_dungeonLevel", plugin);
      player.removeMetadata("_edit_editMode", plugin);

      player.sendMessage(ChatColor.GREEN + "Barreiras salvas");

      return true;
    }

    String editMode = "set";

    if (args[1].equalsIgnoreCase("add")) {
      editMode = args[1];
    }

    String dungeonLevel = args[0];


    player.setMetadata("_edit_dungeonId", new FixedMetadataValue(plugin, dungeonId.get()));
    player.setMetadata("_edit_dungeonLevel", new FixedMetadataValue(plugin, dungeonLevel));
    player.setMetadata("_edit_editMode", new FixedMetadataValue(plugin, editMode));

    player.sendMessage(ChatColor.GOLD + "Dungeon level: " + ChatColor.BLUE + dungeonLevel + ChatColor.GOLD + "\nEdit mode: " + ChatColor.BLUE + editMode);

    return true;
  }
}

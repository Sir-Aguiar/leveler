package org.aguiar.leveler.events;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.utils.LevelerPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class RaidZombieDeathListener implements Listener {

  private final Leveler plugin;

  public RaidZombieDeathListener(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onDeath(EntityDeathEvent event) {
    Zombie entity = (Zombie) event.getEntity();

    if (!entity.hasMetadata("isRaid")) {
      return;
    }

    String customName = ChatColor.stripColor(entity.getCustomName());
    Player player = entity.getKiller();

    assert player != null;

    float baseExperience = entity.getMetadata("baseExperience").stream().findFirst().map(MetadataValue::asFloat).orElse(0.0f);
    float level = entity.getMetadata("level").stream().findFirst().map(MetadataValue::asFloat).orElse(0.0f);
    float newExp = Math.min(player.getExp() + (level * baseExperience), 1.0f);
    player.setExp(newExp);

    player.sendMessage(String.format("Você ganhou %.2f de experiência!", baseExperience));
  }


  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Bukkit.getConsoleSender().sendMessage("Jogador entrou com sucesso");
  }
}

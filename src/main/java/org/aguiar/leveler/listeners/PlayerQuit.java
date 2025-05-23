package org.aguiar.leveler.listeners;

import org.aguiar.leveler.utils.WorldsManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerQuit implements Listener {
  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    List<World> playerWorlds = WorldsManager.getPlayerWorlds(player);

    playerWorlds.forEach(WorldsManager::deleteWorld);
  }
}

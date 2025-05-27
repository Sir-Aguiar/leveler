package org.aguiar.leveler.entities.raids;

import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public abstract class Raid {
  private final World world;
  private final List<RaidMob> mobs = new ArrayList<>();

  public Raid(World world) {
    this.world = world;
  }

  public void startRaid() {

  }
}

package org.aguiar.leveler.entities.raids;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Raid {
  private final List<Entity> mobs = new ArrayList<>();

  public Monster spawnMob(Location location, RaidMob raidMob) {
    World world = location.getWorld();
    assert world != null;

    Monster spawnedEntity = (Monster) world.spawnEntity(location, raidMob.entityType());

    ItemStack testItem = new ItemStack(Material.CHAINMAIL_HELMET);

    spawnedEntity.getEquipment().getItem().add

    return spawnedEntity;
  }

  public void startRaid() {

  }
}

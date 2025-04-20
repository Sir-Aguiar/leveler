package org.aguiar.leveler.entities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class RaidZombie {
  private Zombie zombie;

  public Zombie spawnBoss(Location location) {
    World world = location.getWorld();

    zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);

    zombie.setCustomName(String.format("%s%SChefe", ChatColor.BOLD, ChatColor.GOLD));
    zombie.setCustomNameVisible(true);


    zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10.0);
    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2);
    zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100.0);
    zombie.setHealth(100.0);

    zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
    zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
    zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
    zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
    zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));

    return zombie;
  }

  public Zombie spawnWorker(Location location) {
    World world = location.getWorld();

    zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);

    zombie.setCustomName(String.format("%sCapanga", ChatColor.GRAY));
    zombie.setCustomNameVisible(true);

    zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2.0);
    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
    zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50.0);
    zombie.setHealth(50.0);

    zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
    zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
    zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
    zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));

    return zombie;
  }
}

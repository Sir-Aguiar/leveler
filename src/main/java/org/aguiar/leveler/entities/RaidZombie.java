package org.aguiar.leveler.entities;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.utils.MobStats;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class RaidZombie {
  private Zombie zombie;
  private final Leveler plugin;
  private final LevelerPlayerData playerData;

  public RaidZombie(Leveler plugin, LevelerPlayerData playerData) {
    this.plugin = plugin;
    this.playerData = playerData;
  }

  public Zombie spawnBoss(Location location) {
    World world = location.getWorld();

    double zombieHp = MobStats.getScaledZombieBossHp(playerData);
    double zombieDamage = MobStats.getScaledZombieBossDamage(playerData);

    zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);

    zombie.setCustomName(String.format("%s%SChefe", ChatColor.BOLD, ChatColor.GOLD));
    zombie.setCustomNameVisible(true);

    zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(zombieDamage);
    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25);
    zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(zombieHp);
    zombie.setHealth(zombieHp);

    zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
    zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
    zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
    zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
    zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));

    zombie.setMetadata("isRaid", new FixedMetadataValue(plugin, true));
    zombie.setMetadata("experienceFactor", new FixedMetadataValue(plugin, 0.84f));
    zombie.setMetadata("type", new FixedMetadataValue(plugin, "Boss"));

    return zombie;
  }

  public Zombie spawnWorker(Location location) {
    World world = location.getWorld();

    double zombieHp = MobStats.getScaledZombieSoldierHp(playerData);
    double zombieDamage = MobStats.getScaledZombieSoldierDamage(playerData);

    zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);

    zombie.setCustomName(String.format("%sCapanga", ChatColor.GRAY));
    zombie.setCustomNameVisible(true);

    zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(zombieDamage);
    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.35);
    zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(zombieHp);
    zombie.setHealth(zombieHp);

    zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
    zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
    zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
    zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));

    zombie.setMetadata("isRaid", new FixedMetadataValue(plugin, "true"));
    zombie.setMetadata("experienceFactor", new FixedMetadataValue(plugin, 1.52f));
    zombie.setMetadata("type", new FixedMetadataValue(plugin, "Soldier"));
    return zombie;
  }
}

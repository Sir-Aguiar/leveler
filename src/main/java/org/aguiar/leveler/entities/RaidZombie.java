package org.aguiar.leveler.entities;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
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
  private final Leveler plugin;
  private final PlayerProgression playerData;

  public RaidZombie(Leveler plugin, PlayerProgression playerData) {
    this.plugin = plugin;
    this.playerData = playerData;
  }

  public Zombie spawnBoss(Location location) {
    World world = location.getWorld();
    assert world != null;
    Zombie zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);

    double zombieHp = Math.min(MobStats.getScaledZombieBossHp(playerData), 2048.0);
    double zombieDamage = Math.min(MobStats.getScaledZombieBossDamage(playerData), 32.0);

    zombie.setCustomName(String.format("%s%SChefe", ChatColor.BOLD, ChatColor.GOLD));
    zombie.setCustomNameVisible(true);

    // Stats
    zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(zombieDamage);
    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25);
    zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(zombieHp);
    zombie.setHealth(zombieHp);

    // Loot
    zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
    zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
    zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
    zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
    zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));

    // Metadata
    zombie.setMetadata("isRaid", new FixedMetadataValue(plugin, true));
    zombie.setMetadata("xpDrop", new FixedMetadataValue(plugin, MobStats.getZombieScaledXp(playerData.getPlayerLevel(), ZombieClasses.BOSS)));
    zombie.setMetadata("type", new FixedMetadataValue(plugin, ZombieClasses.BOSS));
    zombie.setAdult();

    plugin.getLogger().info(String.format("[ZombieSpawn] Type: BOSS, HP: %.2f, Damage: %.2f, XP Drop: %.1f", zombieHp, zombieDamage,
            MobStats.getZombieScaledXp(playerData.getPlayerLevel(), ZombieClasses.BOSS)));

    return zombie;
  }

  public Zombie spawnSoldier(Location location) {
    World world = location.getWorld();
    assert world != null;
    Zombie zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);

    double zombieHp = Math.min(MobStats.getScaledZombieSoldierHp(playerData), 2048.0);
    double zombieDamage = Math.min(MobStats.getScaledZombieSoldierDamage(playerData), 32.0);

    zombie.setCustomName(String.format("%s%sSoldado", ChatColor.BOLD, ChatColor.RED));
    zombie.setCustomNameVisible(true);

    // Stats
    zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(zombieDamage);
    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.35);
    zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(zombieHp);
    zombie.setHealth(zombieHp);

    // Loot
    zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
    zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
    zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
    zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
    zombie.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD));

    // Metadata
    zombie.setMetadata("isRaid", new FixedMetadataValue(plugin, true));
    zombie.setMetadata("xpDrop", new FixedMetadataValue(plugin, MobStats.getZombieScaledXp(playerData.getPlayerLevel(), ZombieClasses.SOLDIER)));
    zombie.setMetadata("type", new FixedMetadataValue(plugin, ZombieClasses.BOSS));
    zombie.setAdult();

    plugin.getLogger().info(String.format("[ZombieSpawn] Type: SOLDIER, HP: %.2f, Damage: %.2f, XP Drop: %1f", zombieHp, zombieDamage, MobStats.getZombieScaledXp(playerData.getPlayerLevel(), ZombieClasses.BOSS)));

    return zombie;
  }
}

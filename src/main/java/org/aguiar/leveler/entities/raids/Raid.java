package org.aguiar.leveler.entities.raids;

import org.aguiar.leveler.Leveler;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Raid {
  private final Leveler plugin;
  private final List<Entity> mobs = new ArrayList<>();
  private final Player player;

  public Raid(Leveler plugin, Player player) {
    this.plugin = plugin;
    this.player = player;
  }

  public Monster spawnMob(World world, RaidMob raidMob) {
    Location location = new Location(world, raidMob.getSpawnLocation().get("x"), raidMob.getSpawnLocation().get("y"), raidMob.getSpawnLocation().get("z"));

    Monster spawnedEntity = (Monster) world.spawnEntity(location, raidMob.getEntityType());
    spawnedEntity.setCustomName(raidMob.getName());

    raidMob.getEquipments().forEach((slot, mobEquipment) -> {
      ItemStack itemStack = new ItemStack(mobEquipment.material(), 1);

      ItemMeta itemMeta = itemStack.getItemMeta();

      assert itemMeta != null;

      itemMeta.setDisplayName(mobEquipment.name());
      itemMeta.setLore(mobEquipment.lore());
      itemStack.setItemMeta(itemMeta);

      mobEquipment.enchantments().forEach((enchantment, level) -> {
        itemStack.addEnchantment(enchantment, level);
      });

      Objects.requireNonNull(spawnedEntity.getEquipment()).setItem(slot, itemStack);
    });


    spawnedEntity.setMetadata("isRaid", new FixedMetadataValue(plugin, true));

    double xpDrop = 2; /*MobStats.getScaledXP(playerProgressions, MobClass.valueOf(raidMob.mobClass()));*/
    spawnedEntity.setMetadata("xpDrop", new FixedMetadataValue(plugin, xpDrop));

    spawnedEntity.setMetadata("type", new FixedMetadataValue(plugin, raidMob.getMobClass()));

    mobs.add(spawnedEntity);

    return spawnedEntity;
  }
}

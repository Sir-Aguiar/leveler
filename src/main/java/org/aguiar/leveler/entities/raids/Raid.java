package org.aguiar.leveler.entities.raids;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.entities.MobClass;
import org.aguiar.leveler.utils.MobStats;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
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
  private List<PlayerProgression> playerProgressions = new ArrayList<>();

  public Raid(Leveler plugin, List<PlayerProgression> playerProgressions) {
    this.plugin = plugin;
    this.playerProgressions = playerProgressions;
  }

  public Raid(Leveler plugin, PlayerProgression playerProgression) {
    this.plugin = plugin;
    playerProgressions.add(playerProgression);
  }

  public Monster spawnMob(World world, RaidMob raidMob) {
    Location location = new Location(
            world,
            raidMob.spawnLocation().get("x"),
            raidMob.spawnLocation().get("y"),
            raidMob.spawnLocation().get("z")
    );

    Monster spawnedEntity = (Monster) world.spawnEntity(location, raidMob.entityType());

    raidMob.equipments().forEach((slot, mobEquipment) -> {
      ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(mobEquipment.material())), 1);

      ItemMeta itemMeta = itemStack.getItemMeta();

      assert itemMeta != null;

      itemMeta.setDisplayName(mobEquipment.name());
      itemMeta.setLore(mobEquipment.lore());
      itemStack.setItemMeta(itemMeta);

      EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(slot);

      mobEquipment.enchantments().forEach((enchantment, integer) -> {
        itemStack.addEnchantment(Objects.requireNonNull(Registry.ENCHANTMENT.get(NamespacedKey.minecraft(enchantment))), integer);
      });

      Objects.requireNonNull(spawnedEntity.getEquipment()).setItem(equipmentSlot, itemStack);
    });


    spawnedEntity.setMetadata("isRaid", new FixedMetadataValue(plugin, true));

    double xpDrop = MobStats.getScaledXP(playerProgressions, MobClass.valueOf(raidMob.mobClass()));
    spawnedEntity.setMetadata("xpDrop", new FixedMetadataValue(plugin, xpDrop));

    spawnedEntity.setMetadata("type", new FixedMetadataValue(plugin, MobClass.valueOf(raidMob.mobClass())));

    mobs.add(spawnedEntity);

    return spawnedEntity;
  }

  public void startRaid() {

  }
}

package org.aguiar.leveler.entities.raids;

import org.aguiar.leveler.Leveler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

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
    /*raidMob.equipments().forEach((slot, mobEquipment) -> {
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
    });*/


    spawnedEntity.setMetadata("isRaid", new FixedMetadataValue(plugin, true));

    double xpDrop = 2; /*MobStats.getScaledXP(playerProgressions, MobClass.valueOf(raidMob.mobClass()));*/
    spawnedEntity.setMetadata("xpDrop", new FixedMetadataValue(plugin, xpDrop));

    spawnedEntity.setMetadata("type", new FixedMetadataValue(plugin, raidMob.getMobClass()));

    mobs.add(spawnedEntity);

    return spawnedEntity;
  }
}

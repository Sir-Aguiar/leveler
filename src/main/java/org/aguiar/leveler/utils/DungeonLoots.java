package org.aguiar.leveler.utils;

import org.aguiar.leveler.database.entities.LootItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;

public interface DungeonLoots {
  LootItem PIGLIN_DREAMS = new LootItem(
          "Piglin Dreams",
          "This is useless",
          Material.GOLDEN_HOE,
          1,
          1,
          0.1f,
          Map.of(Enchantment.FORTUNE, 3, Enchantment.KNOCKBACK, 2)
  );
}

package org.aguiar.leveler.database.entities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;

public record LootItem(String name, String lore, Material material, Integer minAmount, Integer maxAmount, Float probability,
                       Map<Enchantment, Integer> enchantments) {
}

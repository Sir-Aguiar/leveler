package org.aguiar.leveler.entities.raids;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.Map;

public record MobEquipment(Material material, String name, Integer amount, List<String> lore,
                           Map<Enchantment, Integer> enchantments) {

}

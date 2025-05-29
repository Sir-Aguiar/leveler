package org.aguiar.leveler.entities.raids;

import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;



public record RaidMob(
        String name,
        EntityType entityType,
        List<Map<Attribute, Double>> attributes,
        List<Map<EquipmentSlot, ItemStack>> equipments,
        List<Map<EquipmentSlot, Enchantment>> enchantments,
        double health,
        double damage,
        double xpDrop
) {

}

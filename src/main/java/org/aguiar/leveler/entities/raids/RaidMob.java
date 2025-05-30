package org.aguiar.leveler.entities.raids;

import org.bukkit.entity.EntityType;

import java.util.Map;


public record RaidMob(
        String name,
        String mobClass,
        EntityType entityType,
        Map<String, Double> spawnLocation,
        Map<String, MobEquipment> equipments,
        double health,
        double damage,
        double xpDrop

) {

}

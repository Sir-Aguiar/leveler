package org.aguiar.leveler.entities.raids;

import java.util.List;
import java.util.Map;

public record MobEquipment(String material, String name, List<String> lore, Map<String, Integer> enchantments) {

}

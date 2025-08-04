package org.aguiar.leveler.entities.loot;

public enum LootClass {
  RESOURCE, EQUIPMENT, CONSUMABLE;

  public static LootClass fromString(String lootClass) {
    return switch (lootClass.toUpperCase()) {
      case "RESOURCE" -> RESOURCE;
      case "EQUIPMENT" -> EQUIPMENT;
      case "CONSUMABLE" -> CONSUMABLE;
      default -> throw new IllegalArgumentException("Unknown loot class: " + lootClass);
    };
  }
}

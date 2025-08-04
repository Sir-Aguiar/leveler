package org.aguiar.leveler.entities;

public enum LootClass {
  ATTRIBUTES, ITEM, CONSUMABLE;

  public static LootClass fromString(String lootClass) {
    return switch (lootClass.toUpperCase()) {
      case "ATTRIBUTES" -> ATTRIBUTES;
      case "ITEM" -> ITEM;
      case "CONSUMABLE" -> CONSUMABLE;
      default -> throw new IllegalArgumentException("Unknown loot class: " + lootClass);
    };
  }
}

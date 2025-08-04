package org.aguiar.leveler.entities.loot;

public enum LootRarity {
  COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTHIC, DIVINE;

  public static LootRarity fromString(String rarity) {
    return switch (rarity.toUpperCase()) {
      case "COMMON" -> COMMON;
      case "UNCOMMON" -> UNCOMMON;
      case "RARE" -> RARE;
      case "EPIC" -> EPIC;
      case "LEGENDARY" -> LEGENDARY;
      case "MYTHIC" -> MYTHIC;
      case "DIVINE" -> DIVINE;
      default -> throw new IllegalArgumentException("Unknown rarity: " + rarity);
    };
  }
}

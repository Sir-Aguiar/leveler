package org.aguiar.leveler.utils;

import org.aguiar.leveler.database.entities.LootItem;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootGenerator {
  private final Random random = new Random();
  private final List<LootItem> BASE_LOOT = new ArrayList<>();
  private final List<LootItem> RANK_C_LOOT = new ArrayList<>();
  private final List<LootItem> RANK_B_LOOT = new ArrayList<>();
  private final List<LootItem> RANK_A_LOOT = new ArrayList<>();
  private final List<LootItem> RANK_S_LOOT = new ArrayList<>();


  public LootGenerator() {
    BASE_LOOT.add(DungeonLoots.PIGLIN_DREAMS);
  }

  public void generateLoot(PlayerProgression playerData, Chest chest) {
    Inventory inventory = chest.getInventory();
    inventory.clear();

    List<Integer> occupiedSlots = new ArrayList<>();

    char playerRank = playerData.getRank();

    int amountOfLoot = (int) Math.min(1, Math.random() * ((double) inventory.getSize() / 4));
  }

  public List<LootItem> getBaseLoot() {
    return BASE_LOOT;
  }

  public List<LootItem> getRankCLoot() {
    return RANK_C_LOOT;
  }

  public List<LootItem> getRankBLoot() {
    return RANK_B_LOOT;
  }

  public List<LootItem> getRankALoot() {
    return RANK_A_LOOT;
  }

  public List<LootItem> getRankSLoot() {
    return RANK_S_LOOT;
  }
}

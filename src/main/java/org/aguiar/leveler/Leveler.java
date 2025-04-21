package org.aguiar.leveler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.aguiar.leveler.commands.StartRaid;
import org.aguiar.leveler.events.PlayerJoin;
import org.aguiar.leveler.events.RaidZombieDeathListener;
import org.aguiar.leveler.entities.LevelerPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class Leveler extends JavaPlugin {
  private final String PLUGIN_NAME = getName();
  public final File playerDataFile = new File(getDataFolder(), "playerData.json");
  public Map<String, LevelerPlayerData> playersData = new HashMap<>();
  public Gson gson = new Gson();
  public Type playerDataType = new TypeToken<Map<String, LevelerPlayerData>>() {
  }.getType();


  @Override
  public void onEnable() {

    getDataFolder().mkdirs();
    startConfigs();
    loadPlayerData();

    this.getCommand("start-raid").setExecutor(new StartRaid(this));

    getServer().getPluginManager().registerEvents(new RaidZombieDeathListener(this), this);
    getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);

    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Enabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

  @Override
  public void onDisable() {

    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Disabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

  public void startConfigs() {
    try {
      if (!playerDataFile.exists()) {
        playerDataFile.createNewFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public synchronized void loadPlayerData() {
    try (Reader reader = new FileReader(playerDataFile)) {
      playersData = gson.fromJson(reader, playerDataType);

      if (playersData == null) {
        playersData = new HashMap<>();
      }

      System.out.println("Dados carregados: " + playersData.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized void savePlayerData() {
    try (Writer playerDataWriter = new FileWriter(playerDataFile, false)) {
      if (playersData == null) {
        System.out.println("NULO");
      } else {
        System.out.println(playersData.toString());
      }

      gson.toJson(playersData, playerDataWriter);
      playerDataWriter.close();
      loadPlayerData();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

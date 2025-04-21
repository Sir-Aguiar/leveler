package org.aguiar.leveler.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.aguiar.leveler.entities.LevelerPlayerData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LevelerPlayerDataManager {
  private final File playerDataFile;
  private final Gson gson;
  private final Type playerDataType;
  private Map<String, LevelerPlayerData> playersData;


  public LevelerPlayerDataManager(File playerDataFile) {
    this.playerDataFile = playerDataFile;
    this.gson = new Gson();
    this.playerDataType = new TypeToken<Map<String, LevelerPlayerData>>() {
    }.getType();
    this.playersData = new HashMap<>();
    initializeFile();
    loadPlayerData();
  }

  private void initializeFile() {
    try {
      if (!playerDataFile.exists()) {
        playerDataFile.getParentFile().mkdirs();
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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized void savePlayerData() {
    try (Writer writer = new FileWriter(playerDataFile, false)) {
      gson.toJson(playersData, writer);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized LevelerPlayerData getPlayerData(String playerId) {
    return playersData.get(playerId);
  }

  public synchronized void updatePlayerData(String playerId, LevelerPlayerData data) {
    playersData.put(playerId, data);
    savePlayerData();
  }

  public synchronized Map<String, LevelerPlayerData> getAllPlayerData() {
    return new HashMap<>(playersData);
  }
}

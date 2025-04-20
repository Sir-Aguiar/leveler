package org.aguiar.leveler;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aguiar.leveler.commands.StartRaid;
import org.aguiar.leveler.events.RaidZombieDeathListener;
import org.aguiar.leveler.utils.LevelerPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class Leveler extends JavaPlugin {
  private final String PLUGIN_NAME = getName();
  private Gson gson;
  private File dataFile;
  private Map<UUID, LevelerPlayerData> playerDataMap = new ConcurrentHashMap<>();

  @Override
  public void onEnable() {
    this.gson = new GsonBuilder().setPrettyPrinting().create();

    if (!getDataFolder().exists()) {
      getDataFolder().mkdirs();
    }

    this.dataFile = new File(getDataFolder(), "playerData.json");

    if (!dataFile.exists()) {
      try {
        dataFile.createNewFile();
        getLogger().info("Arquivo playerData.json criado.");
        saveData();
      } catch (IOException e) {
        getLogger().severe("Nao foi possivel criar o arquivo playerData.json!");
        e.printStackTrace();
        getServer().getPluginManager().disablePlugin(this);
        return;
      }
    }

    this.getCommand("start-raid").setExecutor(new StartRaid(this));
    getServer().getPluginManager().registerEvents(new RaidZombieDeathListener(this), this);

    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Enabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

  @Override
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage(String.format("[%s] - Disabled Successfully", PLUGIN_NAME.toUpperCase()));
  }

  public void saveData() {
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(dataFile), StandardCharsets.UTF_8)) {
      gson.toJson(this.playerDataMap, writer);
    } catch (IOException e) {
      getLogger().severe("Erro ao salvar os dados em playerData.json!");
      e.printStackTrace();
    }
  }

  public void loadData() {
    try (Reader reader = new InputStreamReader(new FileInputStream(dataFile), StandardCharsets.UTF_8)) {
      Type mapType = new TypeToken<ConcurrentHashMap<UUID, LevelerPlayerData>>() {
      }.getType();
      Map<UUID, LevelerPlayerData> loadedMap = gson.fromJson(reader, mapType);

      if (loadedMap != null) {
        this.playerDataMap.clear();
        this.playerDataMap.putAll(loadedMap);
        getLogger().info("Dados dos jogadores carregados de playerData.json. " + loadedMap.size() + " entradas.");
      } else {
        getLogger().warning("Arquivo playerData.json estava vazio ou mal formatado. Iniciando com mapa de dados vazio.");
        this.playerDataMap = new ConcurrentHashMap<>();
      }

    } catch (FileNotFoundException e) {
      getLogger().warning("Arquivo playerData.json nao encontrado (isso eh normal na primeira vez). Um novo sera criado.");
      this.playerDataMap = new ConcurrentHashMap<>();
    } catch (IOException e) {
      getLogger().severe("Erro ao ler o arquivo playerData.json!");
      e.printStackTrace();

    } catch (com.google.gson.JsonSyntaxException e) {
      getLogger().severe("Erro de sintaxe no arquivo playerData.json! Verifique a formatação do arquivo.");
      e.printStackTrace();
      this.playerDataMap = new ConcurrentHashMap<>();
    }
  }
}

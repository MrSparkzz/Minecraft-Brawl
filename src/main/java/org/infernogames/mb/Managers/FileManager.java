package org.infernogames.mb.Managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.infernogames.mb.ItemHandler;
import org.infernogames.mb.Reward;
import org.infernogames.mb.Reward.RewardType;
import org.infernogames.mb.Arena.Arena;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class FileManager {
   
   public static File dataFolder;
   
   private String name;
   private FileStorage storage;
   
   public static FileManager getFromArena(Arena arena) {
      return getFromArenaName(arena.getName());
   }
   
   public static FileManager getFromArenaName(String name) {
      return new FileManager("Arenas" + File.separator + name);
   }
   
   public FileManager(String name) {
      this.name = name;
      File file = new File(dataFolder + File.separator + name + ".yml");
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      storage = new FileStorage(file);
   }
   
   public void addDefault(String path, Object thing) {
      if (!getConfig().isSet(path)) {
         getConfig().set(path, thing);
      }
   }
   
   public Reward getReward(String path) {
      Reward r = null;
      String result = getConfig().getString(path);
      if (result.startsWith("$")) {
         r = new Reward(RewardType.Cash, Integer.parseInt(result.replaceFirst("$", "")));
      } else {
         r = new Reward(RewardType.Item, ItemHandler.fromString(result));
      }
      return r;
   }
   
   public void setReward(String path, Reward reward) {
      if (reward.getType() == RewardType.Cash) {
         getConfig().set(path, "$" + reward.getCash());
      } else {
         getConfig().set(path, ItemHandler.toString(reward.getReward()));
      }
   }
   
   public FileConfiguration getConfig() {
      return getStorage().getConfig();
   }
   
   private FileStorage getStorage() {
      return storage;
   }
   
   public void saveConfig() {
      try {
         getStorage().save();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   public void reloadConfig() {
      getStorage().reload();
   }
   
   public String getName() {
      return name;
   }
   
   private static HashMap<File, FileConfiguration> configs = new HashMap<File, FileConfiguration>();
   
   public class FileStorage {
      
      private FileConfiguration config;
      private File file;
      
      public FileStorage(File file) {
         this.file = file;
         reload();
      }
      
      public void save() throws IOException {
         config.save(file);
      }
      
      public void reload() {
         for (File file : configs.keySet()) {
            if (file.equals(this.file)) {
               config = configs.get(file);
               return;
            }
         }
         config = YamlConfiguration.loadConfiguration(file);
         configs.put(file, config);
      }
      
      public FileConfiguration getConfig() {
         return config;
      }
      
   }
   
}

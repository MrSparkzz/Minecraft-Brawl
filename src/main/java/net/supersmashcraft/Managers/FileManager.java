package net.supersmashcraft.Managers;

import java.io.File;
import java.io.IOException;

import net.supersmashcraft.ItemHandler;
import net.supersmashcraft.Managers.CreationManager.Reward;
import net.supersmashcraft.Managers.CreationManager.Reward.RewardType;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
   
   public static File dataFolder;
   
   private File file;
   private FileConfiguration config;
   
   public FileManager(String name) {
      file = new File(dataFolder + File.separator + name + ".yml");
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      config = YamlConfiguration.loadConfiguration(file);
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
      return config;
   }
   
   public void saveConfig() {
      try {
         config.save(file);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   public void reloadConfig() {
      config = YamlConfiguration.loadConfiguration(file);
   }
   
}

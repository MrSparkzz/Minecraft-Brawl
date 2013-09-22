package net.supersmashcraft.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.supersmashcraft.Arena.Arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreationManager {
   
   private static HashMap<String, DataHolder> creators = new HashMap<String, DataHolder>();
   
   public static boolean playerStarted(Player p) {
      return creators.containsKey(p.getName());
   }
   
   public static void addPlayer(Player p) {
      creators.put(p.getName(), new DataHolder());
   }
   
   public static DataHolder getHolder(Player p) {
      return creators.get(p.getName());
   }
   
   public static Arena createArena(String path) {
      FileManager man = new FileManager("Arenas");
      FileConfiguration config = man.getConfig();
      List<Location> spawns = new ArrayList<Location>();
      path = "Arenas." + path;
      for (String st : config.getConfigurationSection(path + ".Spawns").getKeys(false)) {
         spawns.add(getFromPath(path + ".Spawns." + st));
      }
      Location[] spawnsArray = new Location[spawns.size()];
      spawns.toArray(spawnsArray);
      Reward reward = man.getReward(path + ".Reward");
      return new Arena(c(path + ".Name", man), getFromPath(path + ".l1"), getFromPath(path + ".l2"),
               getFromPath(path + ".lobby"), getFromPath(path + ".EndPoint"), reward, spawnsArray);
   }
   
   private static String c(String path, FileManager man) {
      return man.getConfig().getString(path);
   }
   
   public static String fromLocation(Location l, boolean block) {
      String world = l.getWorld().getName();
      double x = l.getX();
      double y = l.getY();
      double z = l.getZ();
      
      if (block) {
         x = l.getBlockX();
         y = l.getBlockY();
         z = l.getBlockZ();
      } else {
         x = l.getX();
         y = l.getY();
         z = l.getZ();
      }
      
      String f = world + ";" + x + ";" + y + ";" + z;
      return f;
   }
   
   public static class Reward {
      private RewardType type;
      private ItemStack reward;
      private int cash;
      
      public Reward(RewardType type, Object reward) {
         this.type = type;
         if (reward instanceof ItemStack) {
            this.reward = (ItemStack) reward;
         } else {
            cash = Integer.parseInt(reward.toString());
         }
      }
      
      public RewardType getType() {
         return this.type;
      }
      
      public ItemStack getReward() {
         return this.reward;
      }
      
      public int getCash() {
         return this.cash;
      }
      
      public enum RewardType {
         Cash, Item
      }
   }
   
   public static Location toLocation(String s) {
      String[] fi = s.split(";");
      World w = Bukkit.getWorld(fi[0]);
      double x = Double.parseDouble(fi[1]);
      double y = Double.parseDouble(fi[2]);
      double z = Double.parseDouble(fi[3]);
      
      return new Location(w, x, y, z);
   }
   
   private static Location getFromPath(String path) {
      return toLocation(c(path, new FileManager("Arenas")));
   }
   
   public static class DataHolder {
      public String name = null;
      public Location l1 = null;
      public Location l2 = null;
      public Location lobby = null;
      public List<Location> spawns = new ArrayList<Location>();
      public Location end = null;
      public Reward reward;
      
      public void save() {
         FileManager man = new FileManager("Arenas");
         FileConfiguration c = man.getConfig();
         c.set("Arenas." + name + ".Name", name);
         c.set("Arenas." + name + ".Name", name);
         man.setReward("Arenas." + name + ".Reward", reward);
         lTC("Arenas." + name + ".l1", l1, c, true);
         lTC("Arenas." + name + ".l2", l2, c, true);
         lTC("Arenas." + name + ".lobby", lobby, c, true);
         for (int i = 0; i < spawns.size(); i++) {
            lTC("Arenas." + name + ".Spawns." + i, spawns.get(i), c, false);
         }
         lTC("Arenas." + name + ".EndPoint", end, c, false);
         man.saveConfig();
      }
      
      private void lTC(String path, Location l, FileConfiguration c, boolean block) {
         c.set(path, CreationManager.fromLocation(l, block));
      }
      
      public String check() {
         if (name == null) {
            return "You did not set a name!";
         }
         if (l1 == null) {
            return "You did not set the first position!";
         }
         if (l2 == null) {
            return "You did not set the second position!";
         }
         if (lobby == null) {
            return "You did not set the lobby war[!";
         }
         if (spawns.isEmpty()) {
            return "You did not add any spawns!";
         }
         if (end == null) {
            return "You did not set the end!";
         }
         if (reward == null) {
            return "You did not set a reward!";
         }
         return null;
      }
   }
   
}
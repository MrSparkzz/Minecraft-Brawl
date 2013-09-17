package net.supersmashcraft.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Arena.Arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
      FileConfiguration config = SSCPlugin.instance.getConfig();
      List<Location> spawns = new ArrayList<Location>();
      path = "Arenas." + path;
      for (String st : config.getConfigurationSection(path + ".Spawns").getKeys(false)) {
         spawns.add(getFromPath(path + ".Spawns." + st));
      }
      Location[] spawnsArray = new Location[spawns.size()];
      spawns.toArray(spawnsArray);
      return new Arena(c(path + ".Name"), getFromPath(path + ".l1"), getFromPath(path + ".l2"), getFromPath(path
               + ".EndPoint"), spawnsArray);
   }
   
   private static String c(String path) {
      return SSCPlugin.instance.getConfig().getString(path);
   }
   
   private static Location getFromPath(String path) {
      FileConfiguration config = SSCPlugin.instance.getConfig();
      World w = Bukkit.getWorld(c(path + ".World"));
      int x = config.getInt(path + ".x");
      int y = config.getInt(path + ".y");
      int z = config.getInt(path + ".z");
      return new Location(w, x, y, z);
   }
   
   public static class DataHolder {
      public String name = null;
      public Location l1 = null;
      public Location l2 = null;
      public List<Location> spawns = new ArrayList<Location>();
      public Location end = null;
      
      public void save() {
         FileConfiguration c = SSCPlugin.instance.getConfig();
         c.set("Arenas." + name + ".Name", name);
         c.set("Arenas." + name + ".Name", name);
         lTC("Arenas." + name + ".l1", l1, c);
         lTC("Arenas." + name + ".l2", l2, c);
         for (int i = 0; i < spawns.size(); i++) {
            lTC("Arenas." + name + ".Spawns." + i, spawns.get(i), c);
         }
         lTC("Arenas." + name + ".EndPoint", end, c);
         SSCPlugin.instance.saveConfig();
      }
      
      private void lTC(String path, Location l, FileConfiguration c) {
         c.set(path + ".World", l.getWorld().getName());
         c.set(path + ".x", l.getX());
         c.set(path + ".y", l.getY());
         c.set(path + ".z", l.getZ());
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
         if (spawns.isEmpty()) {
            return "You did not add any spawns!";
         }
         if (end == null) {
            return "You did not set the end!";
         }
         return null;
      }
   }
   
}

package org.infernogames.mb.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LocationUtils {
   public static String fromLocation(Location l, boolean block, boolean by) {
      String world = l.getWorld().getName();
      double x = l.getX();
      double y = l.getY();
      double z = l.getZ();
      double yaw = l.getYaw();
      double pitch = l.getPitch();
      
      if (block) {
         x = l.getBlockX();
         y = l.getBlockY();
         z = l.getBlockZ();
      }
      String f = world + ";" + x + ";" + y + ";" + z;
      if (by) {
         f += ";" + yaw + ";" + pitch;
      }
      return f;
   }
   
   public static String fromLocation(Block l) {
      String world = l.getWorld().getName();
      double x = l.getX();
      double y = l.getY();
      double z = l.getZ();
      
      String f = world + ";" + x + ";" + y + ";" + z;
      return f;
   }
   
   public static Location toLocation(String s) {
      String[] fi = s.split(";");
      World w = Bukkit.getWorld(fi[0]);
      double x = Double.parseDouble(fi[1]);
      double y = Double.parseDouble(fi[2]);
      double z = Double.parseDouble(fi[3]);
      
      if (fi.length == 6) {
         float yaw;
         float pitch;
         yaw = Float.parseFloat(fi[4]);
         pitch = Float.parseFloat(fi[5]);
         
         return new Location(w, x, y, z, yaw, pitch);
      }
      
      return new Location(w, x, y, z);
   }
   
   public static boolean inside(Location l, Location min, Location max) {
      int x = l.getBlockX();
      int y = l.getBlockY();
      int z = l.getBlockZ();
      
      int minX = min.getBlockX();
      int minY = min.getBlockY();
      int minZ = min.getBlockZ();
      
      int maxX = max.getBlockX();
      int maxY = max.getBlockY();
      int maxZ = max.getBlockZ();
      
      return x > minX && x < maxX && y > minY && y < maxY && z > minZ && z < maxZ;
   }
}

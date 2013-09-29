package org.infernogames.mb.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class LocationUtils {
   public static String fromLocation(Location l, boolean block, boolean py) {
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
      if (py) {
         f += ";" + yaw + ";" + pitch;
      }
      return f;
   }
   
   public static String fromLocation(Block l) {
      return l.getWorld().getName() + ";" + l.getX() + ";" + l.getY() + ";" + l.getZ();
   }
   
   public static Location toLocation(String s) {
      String[] fi = s.split(";");
      World w = Bukkit.getWorld(fi[0]);
      double x = Double.parseDouble(fi[1]);
      double y = Double.parseDouble(fi[2]);
      double z = Double.parseDouble(fi[3]);
      if (fi.length == 6) {
         return new Location(w, x, y, z, Float.parseFloat(fi[4]), Float.parseFloat(fi[5]));
      }
      return new Location(w, x, y, z);
   }
   
   public static boolean inside(Location l, Location min, Location max) {
      return l.toVector().isInAABB(min.toVector(), max.toVector());
   }
}

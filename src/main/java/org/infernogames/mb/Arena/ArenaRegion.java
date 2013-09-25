package org.infernogames.mb.Arena;

import java.util.Random;

import org.bukkit.Location;

public class ArenaRegion {
   
   private Arena arena;
   private Location l1, l2, stop, lobby;
   private Location[] spawns;
   
   public ArenaRegion(Arena arena, Location l1, Location l2, Location s, Location l, Location... spawns) {
      this.arena = arena;
      this.l1 = l1;
      this.l2 = l2;
      this.stop = s;
      this.lobby = l;
      this.spawns = spawns;
      
      refreshPoints();
   }
   
   private void refreshPoints() {
      double tmp;
      if (l1.getBlockX() < l2.getBlockX()) {
         tmp = l1.getBlockX();
         l1.setX(l2.getBlockX());
         l2.setX(tmp);
      }
      if (l1.getBlockY() < l2.getBlockY()) {
         tmp = l1.getBlockY();
         l1.setY(l2.getBlockY());
         l2.setY(tmp);
      }
      if (l1.getBlockZ() < l2.getBlockZ()) {
         tmp = l1.getBlockZ();
         l1.setZ(l2.getBlockZ());
         l2.setZ(tmp);
      }
   }
   
   public boolean contains(Location l){
      return l.toVector().isInAABB(l2.toVector(), l1.toVector());
   }
   
   // GETTERS
   
   public Arena getArena() {
      return arena;
   }
   
   public Location getLocationOne() {
      return l1;
   }
   
   public Location getLocationTwo() {
      return l2;
   }
   
   public Location getWarp(WarpType type ){
      switch(type){
      case LOBBY:
         return lobby;
      case SPAWN:
         return spawns[new Random().nextInt(spawns.length)];
      case STOP:
         return stop;
      default:
         return null;
      
      }
   }
   public enum WarpType {
      LOBBY, STOP, SPAWN
   }
}

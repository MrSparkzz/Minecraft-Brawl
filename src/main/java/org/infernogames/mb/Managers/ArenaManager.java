package org.infernogames.mb.Managers;

import java.util.ArrayList;
import java.util.List;

import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;


public class ArenaManager {
   
   private static List<Arena> arenas = new ArrayList<Arena>();
   
   public static void addArena(Arena arena) {
      arenas.add(arena);
   }
   
   public static void deleteArena(Arena arena) {
      if (arenaRegistered(arena)) {
         arena.getManager().stop();
      }
   }
   
   public static boolean arenaRegistered(Arena arena) {
      return arenas.contains(arena);
   }
   
   public static boolean arenaRegistered(String a) {
      for (Arena arena : arenas) {
         if (arena.getName().equalsIgnoreCase(a)) {
            return true;
         }
      }
      return false;
   }
   
   public static Arena getArena(String a) {
      for (Arena arena : arenas) {
         if (arena.getName().equalsIgnoreCase(a)) {
            return arena;
         }
      }
      return null;
   }
   
   public static List<Arena> iterator() {
      return arenas;
   }
   
   // TODO
   
   private PlayerManager man = new PlayerManager();
   
   public PlayerManager getPlayerManager() {
      return man;
   }
   
   private boolean started = false;
   public boolean hasStarted(){
      return started;
   }
   
   public void start(){
      started = true;
   }
   
   public void stop() {
      for(PlayerData data : man.getArenaPlayers()){
         man.stopPlayer(data);
      }
      started = false;
   }
   
}

package net.supersmashcraft.Managers;

import java.util.ArrayList;
import java.util.List;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.Classes.SSCClass;

import org.bukkit.entity.Player;

public class ArenaManager {
   
   private static List<Arena> arenas = new ArrayList<Arena>();
   
   public static void addArena(Arena arena) {
      arenas.add(arena);
   }
   
   public static void deleteArena(Arena arena) {
      arenas.remove(arena);
   }
   
   public static boolean arenaRegistered(Arena arena) {
      return arenas.contains(arena);
   }
   
   public static boolean arenaRegistered(String arena) {
      for (Arena a : arenas) {
         if (a.getName().equalsIgnoreCase(arena)) {
            return true;
         }
      }
      return false;
   }
   
   public static Arena getPlayerArena(Player p) {
      for (Arena arena : arenas) {
         if (arena.getPlayerManager().containsPlayer(p)) {
            return arena;
         }
      }
      return null;
   }
   
   public static boolean isPlayerInArena(Player p) {
      return getPlayerArena(p) == null ? false : true;
   }
   
   public static SSCClass getPlayerClass(Player p) {
      return getPlayerArena(p).getPlayerManager().getPlayerClass(p);
   }
   
   public static List<String> getAllPlayers() {
      List<String> players = new ArrayList<String>();
      for (Arena arena : arenas) {
         players.addAll(arena.getPlayerManager().getPlayers());
      }
      return players;
   }
   
}

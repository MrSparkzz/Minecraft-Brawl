package net.supersmashcraft.Managers;

import java.util.ArrayList;
import java.util.List;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.Classes.SSCClass;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class ArenaManager {
   
   private static List<Arena> arenas = new ArrayList<Arena>();
   
   public static void addArena(final Arena arena) {
      arenas.add(arena);
   }
   
   public static void deleteArena(final Arena arena) {
      arenas.remove(arena);
   }
   
   public static boolean arenaRegistered(final Arena arena) {
      return arenas.contains(arena);
   }
   
   public static boolean arenaRegistered(final String arena) {
      for (final Arena a : arenas) {
         if (a.getName().equalsIgnoreCase(arena)) {
            return true;
         }
      }
      return false;
   }
   
   public static Arena getArena(final String arena) {
      for (final Arena a : arenas) {
         if (a.getName().equalsIgnoreCase(arena)) {
            return a;
         }
      }
      return null;
   }
   
   public static Arena getPlayerArena(final Player p) {
      for (final Arena arena : arenas) {
         if (arena.getPlayerManager().containsPlayer(p)) {
            return arena;
         }
      }
      return null;
   }
   
   public static boolean isPlayerInArena(final Player p) {
      return getPlayerArena(p) != null;
   }
   
   public static SSCClass getPlayerClass(final Player p) {
      return getPlayerArena(p).getPlayerManager().getPlayerClass(p);
   }
   
   public static List<String> getAllPlayers() {
      final List<String> players = new ArrayList<String>();
      for (final Arena arena : arenas) {
         for (PlayerData d : arena.getPlayerManager().getPlayers()) {
            players.add(d.name);
         }
      }
      return players;
   }
   
}

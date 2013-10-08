package org.infernogames.mb.Managers;

import java.util.ArrayList;
import java.util.List;

import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Interfaces.ArenaManager;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class MBArenaManager implements ArenaManager {
   
   private List<Arena> arenas = new ArrayList<Arena>();
   
   @Override
   public void addArena(Arena arena) {
      arenas.add(arena);
   }
   
   @Override
   public void deleteArena(Arena arena) {
      if (arenaRegistered(arena)) {
         arena.stop();
      }
   }
   
   @Override
   public boolean arenaRegistered(Arena arena) {
      return arenas.contains(arena);
   }
   
   @Override
   public boolean arenaRegistered(String a) {
      for (Arena arena : arenas) {
         if (arena.getName().equalsIgnoreCase(a)) {
            return true;
         }
      }
      return false;
   }
   
   @Override
   public Arena getArena(String a) {
      for (Arena arena : arenas) {
         if (arena.getName().equalsIgnoreCase(a)) {
            return arena;
         }
      }
      return null;
   }
   
   @Override
   public List<Arena> iterator() {
      return arenas;
   }
   
   // TODO
   
}

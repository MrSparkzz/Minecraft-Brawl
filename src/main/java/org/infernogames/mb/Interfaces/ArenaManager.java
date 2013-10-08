package org.infernogames.mb.Interfaces;

import java.util.List;

import org.infernogames.mb.Arena.Arena;

public abstract interface ArenaManager {
   public abstract void addArena(Arena arena);
   
   public abstract void deleteArena(Arena arena);
   
   public abstract boolean arenaRegistered(Arena arena);
   
   public abstract boolean arenaRegistered(String a);
   
   public abstract Arena getArena(String a);
   
   public abstract List<Arena> iterator();
}

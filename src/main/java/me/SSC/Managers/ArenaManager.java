package me.SSC.Managers;

import java.util.ArrayList;
import java.util.List;

import me.SSC.Arena.Arena;

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
	
	public static List<String> getAllPlayers(){
		List<String>players = new ArrayList<String>();
		for(Arena arena : arenas){
			players.addAll(arena.getPlayerManager().getPlayers());
		}
		return players;
	}

}

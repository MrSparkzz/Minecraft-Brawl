package me.SSC.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class PlayerManager {

	private List<String> players = new ArrayList<String>();

	public void addPlayer(Player p) {
		players.add(p.getName());
	}

	public void removePlayer(Player p) {
		players.remove(p.getName());
	}

	public void clearPlayers() {
		players.clear();
	}

	public boolean containsPlayer(Player p) {
		return players.contains(p.getName());
	}
	
	public List<String> getPlayers(){
		return this.players;
	}
}

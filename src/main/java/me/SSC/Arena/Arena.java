package me.SSC.Arena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.SSC.Managers.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {

	private PlayerManager pMan;
	private Location l1;
	private Location l2;
	private Location stop;
	private List<Location> spawns = new ArrayList<Location>();

	public Arena(Location l1, Location l2, Location stop, Location... spawns) {
		this.l1 = l1;
		this.l2 = l2;
		this.pMan = new PlayerManager();
		this.stop = stop;
		this.spawns = Arrays.asList(spawns);
	}

	public PlayerManager getPlayerManager() {
		return this.pMan;
	}

	public Location getLocationOne() {
		return this.l1;
	}

	public Location getLocationTwo() {
		return this.l2;
	}

	public Location getRandomSpawn() {
		return spawns.get(new Random().nextInt(spawns.size()));
	}

	public void startArena() {
		for (String s : pMan.getPlayers()) {
			Player p = Bukkit.getPlayerExact(s);
			p.teleport(getRandomSpawn());
		}
	}

	public void endArena() {
		for (String s : pMan.getPlayers()) {
			Player p = Bukkit.getPlayerExact(s);
			p.teleport(stop);
		}
	}

}

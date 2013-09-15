package me.SSC.Arena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.SSC.Managers.PlayerManager;

import org.bukkit.Location;

public class Arena {

	private PlayerManager pMan;
	private Location l1;
	private Location l2;
	private List<Location> spawns = new ArrayList<Location>();

	public Arena(Location l1, Location l2, Location... spawns) {
		this.l1 = l1;
		this.l2 = l2;
		this.pMan = new PlayerManager();
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

}

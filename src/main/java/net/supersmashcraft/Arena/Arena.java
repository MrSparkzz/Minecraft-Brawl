package net.supersmashcraft.Arena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.supersmashcraft.Managers.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {
   
   private final PlayerManager pMan;
   private final String name;
   private final Location l1;
   private final Location l2;
   private final Location stop;
   private List<Location> spawns = new ArrayList<Location>();
   
   public Arena(final String name, final Location l1, final Location l2, final Location stop,
            final Location... spawns) {
      this.name = name;
      this.l1 = l1;
      this.l2 = l2;
      this.pMan = new PlayerManager();
      this.stop = stop;
      this.spawns = Arrays.asList(spawns);
   }
   
   public String getName() {
      return this.name;
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
      for (final String s : pMan.getPlayers()) {
         final Player p = Bukkit.getServer().getPlayerExact(s);
         p.teleport(getRandomSpawn());
      }
   }
   
   public void endArena() {
      for (final String s : pMan.getPlayers()) {
         final Player p = Bukkit.getServer().getPlayerExact(s);
         p.teleport(stop);
      }
   }
}

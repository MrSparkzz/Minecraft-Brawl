package net.supersmashcraft.Arena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Managers.PlayerManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Arena {
   
   private final PlayerManager pMan;
   private final String name;
   private final Location l1;
   private final Location l2;
   private final Location stop;
   private List<Location> spawns = new ArrayList<Location>();
   private ScoreboardManager manager = Bukkit.getScoreboardManager();
   
   public Arena(final String name, final Location l1, final Location l2, final Location stop,
            final Location... spawns) {
      this.name = name;
      this.l1 = l1;
      this.l2 = l2;
      this.pMan = new PlayerManager();
      this.stop = stop;
      this.spawns = Arrays.asList(spawns);
      BukkitRunnable s = new BukkitRunnable() {
         @Override
         public void run() {
            refreshScoreboard();
         }
      };
      s.runTaskTimer(SSCPlugin.instance, 0, 20);
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
   
   public Location getStop() {
      return stop;
   }
   
   public Location getRandomSpawn() {
      return spawns.get(new Random().nextInt(spawns.size()));
   }
   
   public void refreshScoreboard() {
      Scoreboard board = manager.getNewScoreboard();
      Objective objective = board.registerNewObjective("test", "dummy");
      objective.setDisplayName("Players  -  Lives");
      objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      for (PlayerData data : pMan.getPlayers()) {
         Player p = Bukkit.getPlayer(data.name);
         Score score = objective.getScore(p);
         score.setScore(data.lives);
         p.setScoreboard(board);
      }
   }
   
}

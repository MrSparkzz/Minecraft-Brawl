package net.supersmashcraft.Arena;

import java.util.Random;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Classes.SSCClass;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.CreationManager.Reward;
import net.supersmashcraft.Managers.PlayerManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Arena {
   private String name;
   private Location l1;
   private Location l2;
   private Location lobby;
   private Location stop;
   private Reward reward;
   private Location[] spawns;
   
   private Location minLoc;
   private Location maxLoc;
   
   private ArenaManager man;
   private ArenaChecker checker;
   
   public Arena(String name, Location l1, Location l2, Location l, Location s, Reward r, Location... sp) {
      this.name = name;
      this.l1 = l1;
      this.l2 = l2;
      this.lobby = l;
      this.stop = s;
      this.reward = r;
      this.spawns = sp;
      
      this.man = new ArenaManager();
      
      int maxX, maxY, maxZ = 0, minX, minY, minZ = 0;
      if (l1.getBlockX() < l2.getBlockX()) {
         minX = l1.getBlockX();
         maxX = l2.getBlockX();
      } else {
         minX = l2.getBlockX();
         maxX = l1.getBlockX();
      }
      if (l1.getBlockY() < l2.getBlockY()) {
         minY = l1.getBlockY();
         maxY = l2.getBlockY();
      } else {
         minY = l2.getBlockY();
         maxY = l1.getBlockY();
      }
      if (l1.getBlockZ() < l2.getBlockZ()) {
         minX = l1.getBlockZ();
         maxX = l2.getBlockZ();
      } else {
         minX = l2.getBlockZ();
         maxX = l1.getBlockZ();
      }
      minLoc = new Location(l1.getWorld(), minX, minY, minZ);
      maxLoc = new Location(l1.getWorld(), maxX, maxY, maxZ);
      
      checker = new ArenaChecker();
      checker.runTaskTimer(SSCPlugin.instance, 0, 25);
   }
   
   public String getName() {
      return name;
   }
   
   public Location getLocationOne() {
      return l1;
   }
   
   public Location getLocationTwo() {
      return l2;
   }
   
   public Location getMinimumPoint() {
      return minLoc;
   }
   
   public Location getMaximumPoint() {
      return maxLoc;
   }
   
   public Location getLobbyLocation() {
      return lobby;
   }
   
   public Location getStop() {
      return this.stop;
   }
   
   public Reward getReward() {
      return reward;
   }
   
   public Location getRandomSpawn() {
      return spawns[new Random().nextInt(spawns.length)];
   }
   
   public ArenaManager getManager() {
      return man;
   }
   
   public PlayerManager getPlayerManager(){
      return man.getPlayerManager();
   }
   
   private class ArenaChecker extends BukkitRunnable {
      
      @Override
      public void run() {
         if (!man.hasStarted()) {
            if (man.getPlayerManager().getArenaPlayers().size() > 0) {
               // Start
               for (PlayerData data : man.getPlayerManager().getArenaPlayers()) {
                  Player p = data.getPlayer();
                  SSCClass c = data.c;
                  c.setupPlayer(p);
                  p.teleport(getRandomSpawn());
               }
               man.start();
            }
            return;
         }
         if (man.getPlayerManager().getArenaPlayers().size() <= 1) {
            // Finished
            if(getPlayerManager().getArenaPlayers().size() == 1){
               Player p = man.getPlayerManager().getArenaPlayers().get(0).getPlayer();
               p.sendMessage("You won!");
            }
            man.stop();
         }
         Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
         Objective objective = board.registerNewObjective("test", "dummy");
         objective.setDisplayName(ChatColor.GREEN + "Players  " + ChatColor.RED + "   Lives");
         objective.setDisplaySlot(DisplaySlot.SIDEBAR);
         for (PlayerData data : man.getPlayerManager().getArenaPlayers()) {
            Player p = Bukkit.getPlayer(data.name);
            Score score = objective.getScore(p);
            score.setScore(data.lives);
            p.setScoreboard(board);
         }
      }
      
   }
   
}

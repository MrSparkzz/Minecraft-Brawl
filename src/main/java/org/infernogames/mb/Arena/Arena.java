package org.infernogames.mb.Arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.ArenaRegion.WarpType;
import org.infernogames.mb.Classes.SSCClass;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.CreationManager.Reward;
import org.infernogames.mb.Managers.EcoManager;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;
import org.infernogames.mb.Utils.Msg;

public class Arena {
   private String name;
   private Reward reward;
   
   private ArenaManager man;
   private ArenaChecker checker;
   private ArenaRegion region;
   
   private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
   private Objective ob = board.registerNewObjective("test", "dummy");
   
   public Arena(String name, Location l1, Location l2, Location l, Location s, Reward r, Location... sp) {
      this.name = name;
      
      region = new ArenaRegion(this, l1, l2, l, s, sp);
      
      this.reward = r;
      
      this.man = new ArenaManager();
      ob.setDisplayName(ChatColor.GREEN + "Players   " + ChatColor.RED + "   Lives");
      ob.setDisplaySlot(DisplaySlot.SIDEBAR);
      
      checker = new ArenaChecker();
      checker.runTaskTimer(MBPlugin.instance, 0, 25);
   }
   
   public String getName() {
      return name;
   }
   
   public Reward getReward() {
      return reward;
   }
   
   public ArenaManager getManager() {
      return man;
   }
   
   public PlayerManager getPlayerManager() {
      return man.getPlayerManager();
   }
   
   public ArenaRegion getRegion() {
      return region;
   }
   
   private class ArenaChecker extends BukkitRunnable {
      
      @Override
      public void run() {
         if (!man.hasStarted()) {
            if (man.getPlayerManager().getArenaPlayers().size() >= 1) {
               // Start
               for (PlayerData data : man.getPlayerManager().getArenaPlayers()) {
                  Player p = data.getPlayer();
                  SSCClass c = data.c;
                  c.setupPlayer(p);
                  p.teleport(region.getWarp(WarpType.SPAWN));
                  p.sendMessage("Starting!");
               }
               man.start();
            }
            return;
         }
         if (man.getPlayerManager().getArenaPlayers().size() <= 0) {
            // Finished
            if (getPlayerManager().getArenaPlayers().size() == 1) {
               Player p = man.getPlayerManager().getArenaPlayers().get(0).getPlayer();
               switch (reward.getType()) {
               case Cash:
                  try {
                     EcoManager.giveMoney(p, reward.getCash());
                  } catch (ClassNotFoundException e) {
                     Msg.opBroadcast("We tried to give " + p.getName() + " $" + reward.getCash()
                              + " but Vault was not found!");
                  }
                  break;
               case Item:
                  p.getInventory().addItem(reward.getReward());
                  break;
               default:
                  break;
               }
               Msg.broadcast(p.getName() + " has won on the arena " + name + "!");
            }
            man.stop();
         }
         for (PlayerData data : man.getPlayerManager().getArenaPlayers()) {
            Player p = Bukkit.getPlayer(data.name);
            if (p.getScoreboard() != board) {
               p.setScoreboard(board);
            }
            if (data.lives < 1) {
               board.resetScores(p);
            } else {
               // Player is dead
               ob.getScore(p).setScore(data.lives);
               getPlayerManager().stopPlayer(data);
               man.stop();
            }
         }
      }
      
   }
   
}

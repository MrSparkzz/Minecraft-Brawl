package org.infernogames.mb.Arena;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.infernogames.mb.MBClass;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Reward;
import org.infernogames.mb.Arena.ArenaRegion.WarpType;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.EcoManager;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.DeathManager.DeathCause;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;
import org.infernogames.mb.Utils.LocationUtils;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class Arena {
   private String name;
   private List<Reward> rewards = new ArrayList<Reward>();
   
   private ArenaManager man;
   private ArenaChecker checker;
   private ArenaRegion region;
   
   private int arenaPlayers = 0;
   
   private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
   private Objective ob = board.registerNewObjective("test", "dummy");
   private FileConfiguration config = new FileManager("Arenas").getConfig();
   
   public Arena(String name, Location l1, Location l2, Location l, Location s, Reward r, Location... sp) {
      this.name = name;
      
      region = new ArenaRegion(this, l1, l2, l, s, sp);
      rewards.add(r);
      
      this.man = new ArenaManager();
      ob.setDisplayName(ChatColor.GREEN + "Players   " + ChatColor.RED + "   Lives");
      ob.setDisplaySlot(DisplaySlot.SIDEBAR);
      
      checker = new ArenaChecker();
      checker.runTaskTimer(MBPlugin.instance, 0, 25);
   }
   
   public String getName() {
      return name;
   }
   
   public List<Reward> getRewards() {
      return rewards;
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
   
   public void addReward(Reward reward) {
      rewards.add(reward);
   }
   
   private class ArenaChecker extends BukkitRunnable {
      @Override
      public void run() {
         arenaPlayers = getPlayerManager().getArenaPlayers().size();
         for (String s : config.getConfigurationSection("Arenas." + name + ".Signs").getKeys(false)) {
            String value = config.getString("Arenas." + name + ".Signs." + s);
            Location l = LocationUtils.toLocation(s);
            Sign sign = (Sign) l.getBlock().getState();
            if (value.equals("status")) {
               if (man.hasStarted()) {
                  sign.setLine(1, ChatColor.YELLOW + "" + arenaPlayers + "/" + 1);
               } else {
                  sign.setLine(1, ChatColor.LIGHT_PURPLE + "Not Running");
               }
            } else if (value.equals("join")) {
               if (man.hasStarted()) {
                  sign.setLine(1, ChatColor.RED + "Running");
               } else {
                  sign.setLine(1, ChatColor.AQUA + "Click to Join");
               }
            }
            sign.update();
         }
         if (!man.hasStarted()) {
            if (arenaPlayers >= 1) {
               // Start
               for (PlayerData data : man.getPlayerManager().getArenaPlayers()) {
                  Player p = data.getPlayer();
                  MBClass c = data.c;
                  c.setupPlayer(p);
                  p.teleport(region.getWarp(WarpType.SPAWN));
                  p.sendMessage("Starting!");
               }
               man.start();
            }
            return;
         }
         if (arenaPlayers <= 0) {
            // Finished
            if (arenaPlayers == 1) {
               Player p = man.getPlayerManager().getArenaPlayers().get(0).getPlayer();
               for (Reward reward : rewards) {
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
                     Msg.msg(p, "Some weird bug here.. please report it.");
                     break;
                  }
               }
               Msg.broadcast(p.getName() + " has won on the arena " + name + "!");
            }
            man.stop();
         }
         List<PlayerData> players = getPlayerManager().getArenaPlayers();
         Iterator<PlayerData> i = players.iterator();
         while (i.hasNext()) {
            PlayerData data = i.next();
            Player p = data.getPlayer();
            if(!region.contains(p.getLocation())){
               data.removeLife(DeathCause.FALL_OUT);
            }
            if (p.getScoreboard() != board) {
               p.setScoreboard(board);
            }
            if (data.lives < 1) {
               board.resetScores(p);
               getPlayerManager().stopPlayer(data);
               man.stop();
            } else {
               // Player is dead
               ob.getScore(p).setScore(data.lives);
            }
         }
      }
      
   }
   
}

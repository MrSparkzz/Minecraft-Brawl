package org.infernogames.mb.Arena;

import java.util.Iterator;

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
import org.infernogames.mb.Arena.ArenaSettings.ArenaSetting;
import org.infernogames.mb.Managers.EcoManager;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.DeathManager.DeathCause;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;
import org.infernogames.mb.Utils.LocationUtils;
import org.infernogames.mb.Utils.Msg;

public class ArenaChecker {
   
   private Arena arena;
   private int arenaPlayers;
   
   private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
   private Objective ob = board.registerNewObjective("test", "dummy");
   
   public ArenaChecker(Arena arena) {
      this.arena = arena;
      
      new ArenaRunnable().runTaskTimer(MBPlugin.instance, 0, 25);
      
      ob.setDisplayName(ChatColor.GREEN + "Players   " + ChatColor.RED + "   Lives");
      ob.setDisplaySlot(DisplaySlot.SIDEBAR);
   }
   
   public class ArenaRunnable extends BukkitRunnable {
      
      @Override
      public void run() {
         update();
         updateSigns();
         if(arena.getManager().hasStarted()){
            updateStarted();
            if(arenaPlayers > 0){
               updatePlayers();
            }
         } else {
            updateStopped();
         }
      }
      
   }
   
   private void update() {
      arenaPlayers = arena.getPlayerManager().getArenaPlayers().size();
   }
   
   private void updateSigns() {
      FileConfiguration config = new FileManager("Arenas").getConfig();
      if (config.isSet("Arenas." + arena.getName() + ".Signs")) {
         for (String s : config.getConfigurationSection("Arenas." + arena.getName() + ".Signs").getKeys(false)) {
            String value = config.getString("Arenas." + arena.getName() + ".Signs." + s);
            Location l = LocationUtils.toLocation(s);
            Sign sign = (Sign) l.getBlock().getState();
            if (value.equals("status")) {
               if (arena.getManager().hasStarted()) {
                  sign.setLine(1, ChatColor.YELLOW + "" + arenaPlayers + "/" + 1);
               } else {
                  sign.setLine(1, ChatColor.LIGHT_PURPLE + "Not Running");
               }
            } else if (value.equals("join")) {
               if (arena.getManager().hasStarted()) {
                  sign.setLine(1, ChatColor.RED + "Running");
               } else {
                  sign.setLine(1, ChatColor.AQUA + "Click to Join");
               }
            }
            sign.update();
         }
      }
   }
   
   private void updateStarted(){
      if (arenaPlayers <= 0) {
         // Finished
         if (arenaPlayers == 1) {
            Player p = arena.getPlayerManager().getArenaPlayers().get(0).getPlayer();
            for (Reward reward : arena.getRewards()) {
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
            Msg.broadcast(p.getName() + " has won on the arena " + arena.getName() + "!");
         }
         arena.getManager().stop();
      }
   }
   
   private void updateStopped() {
      if (arenaPlayers >= arena.getSettings().getIntSetting(ArenaSetting.STARTING_PLAYERS)) {
         // Start
         for (PlayerData data : arena.getPlayerManager().getArenaPlayers()) {
            Player p = data.getPlayer();
            MBClass c = data.c;
            c.setupPlayer(p);
            p.teleport(arena.getRegion().getWarp(WarpType.SPAWN));
         }
         arena.getManager().start();
      }
   }
   
   private void updatePlayers(){
      Iterator<PlayerData> i = arena.getPlayerManager().getArenaPlayers().iterator();
      while (i.hasNext()) {
         PlayerData data = i.next();
         Player p = data.getPlayer();
         if (!arena.getRegion().contains(p.getLocation())) {
            data.removeLife(DeathCause.FALL_OUT);
         }
         if (p.getScoreboard() != board) {
            p.setScoreboard(board);
         }
         if (data.lives < 1) {
            board.resetScores(p);
            arena.getPlayerManager().stopPlayer(data);
            arena.getManager().stop();
         } else {
            // Player is dead
            ob.getScore(p).setScore(data.lives);
         }
      }
   }
}

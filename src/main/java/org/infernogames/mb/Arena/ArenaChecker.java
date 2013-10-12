package org.infernogames.mb.Arena;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
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
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;
import org.infernogames.mb.Utils.LocationUtils;
import org.infernogames.mb.Utils.Msg;
import org.infernogames.mb.Utils.StaggeredRunnable;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         A BukkitRunnable to check the changing arena data.
 */
public class ArenaChecker {
   
   private Arena arena;
   private int arenaPlayers;
   private long timer;
   private boolean autoRunning = false;
   
   private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
   private Objective ob = board.registerNewObjective("test", "dummy");
   
   private List<BlockState> blocks = new ArrayList<BlockState>();
   
   public ArenaChecker(Arena arena) {
      this.arena = arena;
      timer = arena.getSettings().getIntSetting(ArenaSetting.AUTO_START_TIMER) * 20;
      
      MBPlugin.registerRepeatedRunnable(new ArenaRunnable(), 0, 25);
      
      ob.setDisplayName(ChatColor.GREEN + "Players   " + ChatColor.RED + "   Lives");
      ob.setDisplaySlot(DisplaySlot.SIDEBAR);
      blocks = arena.getRegion().getBlockStates();
   }
   
   public class ArenaRunnable extends BukkitRunnable {
      
      @Override
      public void run() {
         update(); // Will possibly contain more stuff in future
         updateSigns();
         if (arena.hasStarted()) {
            updateStarted();
            updatePlayers();
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
               if (arena.hasStarted()) {
                  sign.setLine(1, ChatColor.YELLOW + "" + arenaPlayers + "/" + 1);
               } else {
                  sign.setLine(1, ChatColor.LIGHT_PURPLE + "Not Running");
               }
            } else if (value.equals("join")) {
               if (arena.hasStarted()) {
                  sign.setLine(1, ChatColor.RED + "Running");
               } else {
                  sign.setLine(1, ChatColor.AQUA + "Click to Join");
               }
            }
            sign.update();
         }
      }
   }
   
   private void updateStarted() {
      if (arenaPlayers <= 1) {
         // Finished
         if (arenaPlayers == 1) {
            Player p = arena.getPlayerManager().getArenaPlayers().get(0).getPlayer();
            for (Reward reward : arena.getRewards()) {
               reward.give(p);
            }
            Msg.broadcast(p.getName() + " has won on the arena " + arena.getName() + "!");
         }
         arena.stop(true);
         rollback();
      }
   }
   
   public void rollback() {
      if (arena.getSettings().getBoolSetting(ArenaSetting.RESTORE_ARENA_ONFINISH)) {
         MBPlugin.registerAsyncTask(new StaggeredRunnable(blocks));
      }
   }
   
   private void updateStopped() {
      if (arenaPlayers >= arena.getSettings().getIntSetting(ArenaSetting.MIN_PLAYERS)) {
         if (!arena.getSettings().getBoolSetting(ArenaSetting.DISABLE_AUTO_START)) {
            if (autoRunning) {
               return;
            }
            autoRunning = true;
            MBPlugin.registerRunnable(new BukkitRunnable() {
               @Override
               public void run() {
                  if (arenaPlayers >= arena.getSettings().getIntSetting(ArenaSetting.MIN_PLAYERS)) {
                     start();
                  } else {
                     autoRunning = false;
                     return;
                  }
               }
            }, timer);
         } else {
            start();
         }
      }
   }
   
   private void start() {
      for (PlayerData data : arena.getPlayerManager().getArenaPlayers()) {
         Player p = data.getPlayer();
         MBClass c = data.c;
         c.setupPlayer(p);
         p.teleport(arena.getRegion().getWarp(WarpType.SPAWN));
      }
      arena.start();
   }
   
   private void updatePlayers() {
      Iterator<PlayerData> i = arena.getPlayerManager().getArenaPlayers().iterator();
      while (i.hasNext()) {
         PlayerData data = i.next();
         Player p = data.getPlayer();
         if (p.getScoreboard() != board) {
            p.setScoreboard(board);
         }
         if (data.lives < 1) {
            board.resetScores(p);
            arena.getPlayerManager().stopPlayer(data);
         } else {
            // Player is dead
            ob.getScore(p).setScore(data.lives);
         }
      }
   }
}

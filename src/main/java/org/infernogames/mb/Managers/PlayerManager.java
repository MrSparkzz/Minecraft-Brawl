package org.infernogames.mb.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Arena.ArenaRegion.WarpType;
import org.infernogames.mb.Classes.SSCClass;
import org.infernogames.mb.Managers.DeathManager.DeathCause;
import org.infernogames.mb.Utils.Msg;

public class PlayerManager {
   
   public static boolean playerInArena(Player p) {
      for (Arena arena : ArenaManager.iterator()) {
         if (arena.getManager().getPlayerManager().hasPlayer(p)) {
            return true;
         }
      }
      return false;
   }
   
   public static Arena getPlayerArena(Player p) {
      for (Arena arena : ArenaManager.iterator()) {
         if (arena.getManager().getPlayerManager().hasPlayer(p)) {
            return arena;
         }
      }
      return null;
   }
   
   public static List<PlayerData> getAllPlayers() {
      List<PlayerData> players = new ArrayList<PlayerData>();
      for (Arena arena : ArenaManager.iterator()) {
         players.addAll(arena.getManager().getPlayerManager().players);
      }
      return players;
   }
   
   private List<PlayerData> players = new ArrayList<PlayerData>();
   
   public void startPlayer(Player p, Arena a, SSCClass c) {
      players.add(new PlayerData(p, a, c));
      p.getInventory().clear();
      p.setGameMode(GameMode.ADVENTURE);
      
      p.teleport(a.getRegion().getWarp(WarpType.LOBBY));
   }
   
   public void stopPlayer(PlayerData p) {
      p.resetData();
      
      p.getPlayer().setFallDistance(0);
      p.getPlayer().teleport(p.a.getRegion().getWarp(WarpType.STOP));
      players.remove(p);
   }
   
   public boolean hasPlayer(Player p) {
      for (PlayerData data : players) {
         if (data.name.equalsIgnoreCase(p.getName())) {
            return true;
         }
      }
      return false;
   }
   
   public PlayerData getPlayer(Player p) {
      for (PlayerData data : players) {
         if (data.name.equalsIgnoreCase(p.getName())) {
            return data;
         }
      }
      return null;
   }
   
   public void broadcast(String msg){
      for(PlayerData data : players){
         Msg.msg(data.getPlayer(), msg);
      }
   }
   
   public List<PlayerData> getArenaPlayers() {
      return players;
   }
   
   public class PlayerData {
      public String name;
      
      public ItemStack[] inventory;
      public ItemStack[] armor;
      
      public float exp;
      public double health;
      public float exhaust;
      public double maxHealth;
      
      public GameMode mode;
      private Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
      
      public Arena a;
      public SSCClass c;
      public int lives;
      
      public PlayerData(Player p, Arena a, SSCClass c) {
         name = p.getName();
         
         inventory = p.getInventory().getContents();
         armor = p.getInventory().getArmorContents();
         
         exp = p.getExp();
         health = p.getHealth();
         exhaust = p.getExhaustion();
         maxHealth = p.getMaxHealth();
         
         mode = p.getGameMode();
         if (p.getScoreboard() != null) {
            b = p.getScoreboard();
         }
         
         lives = 5;
         this.a = a;
         this.c = c;
      }
      
      public void resetData() {
         Player p = getPlayer();
         
         p.getInventory().setContents(inventory);
         p.getInventory().setArmorContents(armor);
         
         p.setExp(exp);
         p.setHealth(0.1);
         p.setMaxHealth(maxHealth);
         p.setHealth(health);
         
         p.setGameMode(mode);
         p.setScoreboard(b);
      }
      
      public void removeLife(DeathCause cause) {
         Player p = getPlayer();
         lives--;
         p.setHealth(p.getMaxHealth());
         p.teleport(a.getRegion().getWarp(WarpType.SPAWN));
      }
      
      public Player getPlayer() {
         return Bukkit.getPlayer(name);
      }
   }
}

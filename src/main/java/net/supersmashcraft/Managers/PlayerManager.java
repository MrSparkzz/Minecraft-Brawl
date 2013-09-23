package net.supersmashcraft.Managers;

import java.util.ArrayList;
import java.util.List;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.Classes.SSCClass;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

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
      
      p.teleport(a.getLobbyLocation());
   }
   
   public void stopPlayer(PlayerData p) {
      p.resetData();
      
      p.getPlayer().setFallDistance(0);
      p.getPlayer().teleport(p.a.getStop());
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
      
      public void removeLife() {
         Player p = getPlayer();
         lives--;
         p.setHealth(p.getMaxHealth());
         p.teleport(a.getRandomSpawn());
      }
      
      public Player getPlayer() {
         return Bukkit.getPlayer(name);
      }
   }
}

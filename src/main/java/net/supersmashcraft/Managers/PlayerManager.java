package net.supersmashcraft.Managers;

import java.util.HashMap;
import java.util.Set;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.ClassUtils.JoinUtils;
import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Classes.SSCClass;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class PlayerManager {
   
   private final HashMap<PlayerData, SSCClass> players = new HashMap<PlayerData, SSCClass>();
   
   // private List<String> players = new ArrayList<String>();
   
   public void addPlayer(final Player p, final SSCClass c) {
      players.put(new PlayerData(p), c);
   }
   
   public void removePlayer(final Player p) {
      PlayerData d = new PlayerData(p);
      for (PlayerData set : players.keySet()) {
         if (set.name == p.getName()) {
            d = set;
            break;
         }
      }
      players.remove(d);
   }
   
   public void clearPlayers() {
      players.clear();
   }
   
   public boolean containsPlayer(final Player p) {
      for (PlayerData set : players.keySet()) {
         if (set.name == p.getName()) {
            return true;
         }
      }
      return false;
   }
   
   public Set<PlayerData> getPlayers() {
      return players.keySet();
   }
   
   public SSCClass getPlayerClass(final Player p) {
      PlayerData d = new PlayerData(p);
      for (PlayerData set : players.keySet()) {
         if (set.name == p.getName()) {
            d = set;
            break;
         }
      }
      return players.get(d);
   }
   
   public PlayerData getPlayerData(Player p) {
      PlayerData d = new PlayerData(p);
      for (PlayerData set : players.keySet()) {
         if (set.name == p.getName()) {
            d = set;
            break;
         }
      }
      return d;
   }
   
   public static class PlayerData {
      public String name;
      public ItemStack[] inventory;
      public ItemStack[] armor;
      public float exp;
      public double health;
      public float exhaust;
      public double maxHealth;
      public GameMode mode;
      public int lives;
      Arena a;
      private Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
      
      public PlayerData(Player p) {
         name = p.getName();
         inventory = p.getInventory().getContents();
         armor = p.getInventory().getArmorContents();
         exp = p.getExp();
         health = p.getHealth();
         exhaust = p.getExhaustion();
         maxHealth = p.getMaxHealth();
         mode = p.getGameMode();
         lives = 5;
         if (p.getScoreboard() != null) {
            b = p.getScoreboard();
         }
         a = ArenaManager.getPlayerArena(p);
      }
      
      public void removeLifes(int amount) {
         Player p = Bukkit.getPlayer(name);
         p.setHealth(p.getMaxHealth());
         if (a == null) {
            Bukkit.getLogger().severe("Arena is null!");
            a = ArenaManager.getPlayerArena(p);
            removeLifes(0);
         } else {
            if (lives <= 1) {
               JoinUtils.stopPlayer(p);
               for (PlayerData d : a.getPlayerManager().getPlayers()) {
                  Msg.msg(Bukkit.getPlayer(d.name), this.name + " has left the arena!");
               }
            }
            lives -= amount;
            a.getPlayerManager().getPlayerClass(p).setupPlayer(p);
            a.refreshScoreboard();
            p.teleport(a.getRandomSpawn());
            p.setHealth(p.getMaxHealth());
            for (PlayerData d : a.getPlayerManager().getPlayers()) {
               Msg.msg(Bukkit.getPlayer(d.name), this.name + " died!");
            }
         }
      }
      
      public void reset() {
         Player p = Bukkit.getPlayer(name);
         p.getInventory().clear();
         p.getInventory().setContents(inventory);
         p.getInventory().setArmorContents(armor);
         p.setExp(exp);
         p.setMaxHealth(maxHealth);
         p.setHealth(health);
         p.setExhaustion(exhaust);
         p.setGameMode(mode);
         p.setScoreboard(b);
      }
   }
}

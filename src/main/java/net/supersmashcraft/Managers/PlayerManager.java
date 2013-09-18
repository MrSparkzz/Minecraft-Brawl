package net.supersmashcraft.Managers;

import java.util.HashMap;
import java.util.Set;

import net.supersmashcraft.Classes.SSCClass;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
      for(PlayerData set : players.keySet()){
         if(set.name == p.getName()){
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
      PlayerData d = new PlayerData(p);
      for(PlayerData set : players.keySet()){
         if(set.name == p.getName()){
            d = set;
            break;
         }
      }
      return players.containsKey(d);
   }
   
   public Set<PlayerData> getPlayers() {
      return players.keySet();
   }
   
   public SSCClass getPlayerClass(final Player p) {
      PlayerData d = new PlayerData(p);
      for(PlayerData set : players.keySet()){
         if(set.name == p.getName()){
            d = set;
            break;
         }
      }
      return players.get(d);
   }
   
   public PlayerData getPlayerData(Player p){
      PlayerData d = new PlayerData(p);
      for(PlayerData set : players.keySet()){
         if(set.name == p.getName()){
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
      
      public PlayerData(Player p) {
         name = p.getName();
         inventory = p.getInventory().getContents();
         armor = p.getInventory().getArmorContents();
         exp = p.getExp();
         health = p.getHealth();
         exhaust = p.getExhaustion();
         maxHealth = p.getMaxHealth();
         mode = p.getGameMode();
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
      }
   }
}

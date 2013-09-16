package net.supersmashcraft.Managers;

import java.util.HashMap;
import java.util.Set;

import net.supersmashcraft.Classes.SSCClass;

import org.bukkit.entity.Player;
/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 *
 */
public class PlayerManager {
   
   private HashMap<String, SSCClass> players = new HashMap<String, SSCClass>();
   
   // private List<String> players = new ArrayList<String>();
   
   public void addPlayer(Player p, SSCClass c) {
      players.put(p.getName(), c);
   }
   
   public void removePlayer(Player p) {
      players.remove(p.getName());
   }
   
   public void clearPlayers() {
      players.clear();
   }
   
   public boolean containsPlayer(Player p) {
      return players.containsKey(p.getName());
   }
   
   public Set<String> getPlayers() {
      return players.keySet();
   }
   
   public SSCClass getPlayerClass(Player p) {
      return players.get(p.getName());
   }
}

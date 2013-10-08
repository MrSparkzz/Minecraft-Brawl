package org.infernogames.mb.Arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.infernogames.mb.Reward;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Main arena class to handle arena info. Stores a majority of
 *         data/objects.
 */
public class Arena {
   private String name;
   private List<Reward> rewards = new ArrayList<Reward>();
   
   private PlayerManager man;
   private ArenaRegion region;
   private ArenaSettings settings;
   
   public Arena(String name, Location l1, Location l2, Location l, Location s, Reward r, Location... sp) {
      this.name = name;
      
      region = new ArenaRegion(this, l1, l2, l, s, sp);
      rewards.add(r);
      
      man = new PlayerManager();
      settings = new ArenaSettings(this);
      new ArenaChecker(this);
   }
   
   public String getName() {
      return name;
   }
   
   public List<Reward> getRewards() {
      return rewards;
   }
   
   public PlayerManager getPlayerManager() {
      return man;
   }
   
   public ArenaRegion getRegion() {
      return region;
   }
   
   public void addReward(Reward reward) {
      rewards.add(reward);
   }
   
   public ArenaSettings getSettings() {
      return settings;
   }
   
   private boolean started = false;
   
   public boolean hasStarted() {
      return started;
   }
   
   public void start() {
      started = true;
   }
   
   public void stop() {
      for (PlayerData data : man.getArenaPlayers()) {
         man.stopPlayer(data);
      }
      started = false;
   }
   
}

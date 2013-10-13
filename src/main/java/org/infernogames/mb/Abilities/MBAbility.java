package org.infernogames.mb.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class MBAbility {
   
   private String name;
   
   public MBAbility(String name) {
      this.name = name;
   }
   
   public void onClick(Player p, Action a, String[] args) {
      
   }
   
   public String name() {
      return name;
   }
   
   public boolean rightClick(Action a) {
      return a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK;
   }
   
   public boolean leftClick(Action a) {
      return a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK;
   }
}

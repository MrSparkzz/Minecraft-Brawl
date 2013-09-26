package org.infernogames.mb.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class MBAbility {
   
   private String name;
   
   public MBAbility(String name) {
      this.name = name;
   }
   
   public void onClick(Player p, Action a) {
      
   }
   
   public String name() {
      return name;
   }
}

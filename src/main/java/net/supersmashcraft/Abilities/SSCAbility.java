package net.supersmashcraft.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class SSCAbility {
   
   private String name;
   
   public SSCAbility(String name) {
      this.name = name;
   }
   
   public void onClick(Player p, Action a) {
      
   }
   
   public String name() {
      return name;
   }
}

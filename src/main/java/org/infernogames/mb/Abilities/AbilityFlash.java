package org.infernogames.mb.Abilities;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.infernogames.mb.Utils.LocationUtils;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class AbilityFlash extends MBAbility {
   
   Material m = Material.PORTAL;
   long cooldown = 40;
   int distance = 6;
   
   public AbilityFlash() {
      super("Flash");
   }
   
   @Override
   public void onClick(final Player p, Action a, String[] args) {
      if (rightClick(a) && p.getItemInHand().getType() == m && !hasCooldown(p)) {
         Block b = LocationUtils.getTargetBlock(p, distance);
         if (!(b.getType() == Material.AIR || b == null)) {
            p.teleport(b.getLocation());
            addRemoveCooldown(p, cooldown);
         }
      }
   }
   
}

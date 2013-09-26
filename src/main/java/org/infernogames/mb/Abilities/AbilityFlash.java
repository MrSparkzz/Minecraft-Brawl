package org.infernogames.mb.Abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;

public class AbilityFlash extends MBAbility {
   
   Material m = Material.PORTAL;
   long cooldown = 40;
   int distance = 6;
   
   public AbilityFlash() {
      super("Flash");
   }
   
   List<String> cooldowns = new ArrayList<String>();
   
   @SuppressWarnings("deprecation")
   @Override
   public void onClick(final Player p, Action a) {
      if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == m
               && !cooldowns.contains(p.getName())) {
         Block b = p.getTargetBlock(null, distance);
         if (!(b.getType() == Material.AIR || b == null)) {
            p.teleport(b.getLocation());
            cooldowns.add(p.getName());
            new BukkitRunnable() {
               @Override
               public void run() {
                  cooldowns.remove(p.getName());
               }
            }.runTaskLater(MBPlugin.instance, cooldown);
         }
      }
   }
   
}

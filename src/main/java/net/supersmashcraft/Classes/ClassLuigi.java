package net.supersmashcraft.Classes;

import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClassLuigi extends SSCClass implements Listener {
   
   public ClassLuigi() {
      super("Luigi", "Your favorite brother!", Material.CLAY_BALL);
      item(Material.BLAZE_ROD);
      armor(Material.LEATHER_HELMET, Color.fromRGB(33, 222, 84));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(33, 222, 84));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(33, 222, 84));
      armor(Material.LEATHER_HELMET, Color.fromRGB(33, 222, 84));
   }
   
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (ArenaManager.isPlayerInArena(event.getPlayer())
               && ArenaManager.getPlayerArena(event.getPlayer()).hasStarted()
               && ArenaManager.getPlayerClass(event.getPlayer()).equals(this)) {
         Player p = event.getPlayer();
         if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                  && p.getItemInHand().getType() == Material.BLAZE_ROD) {
            Arrow a = p.launchProjectile(Arrow.class);
            a.setFireTicks(40);
            a.setTicksLived(10); // Mario and Luigi have the same ability
         }
      }
   }
}
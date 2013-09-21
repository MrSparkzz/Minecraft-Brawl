package net.supersmashcraft.Classes;

import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClassFox extends SSCClass {
   
   public ClassFox() {
      super("Fox", "&7Fox fox fox.. flash!", Material.PORTAL);
      armor(Material.LEATHER_BOOTS, Color.fromRGB(247, 243, 10));
      armor(Material.LEATHER_LEGGINGS, Color.fromRGB(255, 255, 255));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(53, 191, 40));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 255, 255));
      item(Material.GHAST_TEAR);
      // I have to change items and armor color still
      // Fox has two events, ask on skype if your finishing fox.
      // ~Max
   }
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (ArenaManager.isPlayerInArena(event.getPlayer())
               && ArenaManager.getPlayerClass(event.getPlayer()).equals(this)) {
         Player p = event.getPlayer();
         if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                  && p.getItemInHand().getType() == Material.BLAZE_ROD) {
            Arrow a = p.launchProjectile(Arrow.class);
            a.setFireTicks(40);
            a.setTicksLived(40);
         }
      }
   }
}  

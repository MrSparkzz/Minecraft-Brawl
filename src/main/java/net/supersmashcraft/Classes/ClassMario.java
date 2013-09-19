package net.supersmashcraft.Classes;

import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClassMario extends SSCClass implements Listener {
   
   public ClassMario() {
      super("Mario");
      item(Material.BLAZE_ROD);
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 31, 31));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(0, 59, 235));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(16, 71, 235));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 31, 31));
   }
   
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (ArenaManager.isPlayerInArena(event.getPlayer())
               && ArenaManager.getPlayerClass(event.getPlayer()).equals(this)) {
         Player p = event.getPlayer();
         if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                  && p.getItemInHand().getType() == Material.BLAZE_ROD) {
            p.launchProjectile(Fireball.class);
         }
      }
   }
}
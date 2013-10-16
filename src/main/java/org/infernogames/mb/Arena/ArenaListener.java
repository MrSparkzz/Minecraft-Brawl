package org.infernogames.mb.Arena;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.infernogames.mb.Arena.ArenaRegion.WarpType;
import org.infernogames.mb.Arena.ArenaSettings.ArenaSetting;
import org.infernogames.mb.Managers.DeathManager.DeathCause;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

/**
 * 
 * @author Paul, Breezeyboy, MrSparkzz
 * 
 *         Used to listen for certain events happening inside the arena.
 *         Excludes ability listeners.
 */
public class ArenaListener implements Listener {
   
   @EventHandler
   public void onInventoryMove(InventoryClickEvent event) {
      if (event.getWhoClicked() instanceof Player) {
         Player p = (Player) event.getWhoClicked();
         if (PlayerManager.playerInArena(p)) {
            Arena a = PlayerManager.getPlayerArena(p);
            if (!a.getSettings().getBoolSetting(ArenaSetting.INVENTORY_MOVE)) {
               event.setCancelled(true);
            }
         }
      }
   }
   
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         if (!a.getSettings().getBoolSetting(ArenaSetting.LEVER_FLIP)) {
            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_BLOCK) {
               Block block = event.getClickedBlock();
               if (block.equals(Material.LEVER)) event.setCancelled(true);
            }
         }
         if (!a.getSettings().getBoolSetting(ArenaSetting.BUTTON_PRESS)) {
            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_BLOCK) {
               Block block = event.getClickedBlock();
               if (block.equals(Material.WOOD_BUTTON) || block.equals(Material.STONE_BUTTON))
                  event.setCancelled(true);
            }
         }
         if (!a.getSettings().getBoolSetting(ArenaSetting.PLAYER_INTERACT)) {
            event.setCancelled(true);
         }
      }
   }
   
   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         if (!a.getSettings().getBoolSetting(ArenaSetting.BLOCK_BREAK)) {
            event.setCancelled(true);
         }
         if (!a.getSettings().getBoolSetting(ArenaSetting.ITEM_DROP)) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
         }
      }
   }
   
   @EventHandler
   public void onBlockPlace(BlockPlaceEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         if (!a.getSettings().getBoolSetting(ArenaSetting.BLOCK_PLACE)) {
            event.setCancelled(true);
         }
      }
   }
   
   @EventHandler
   public void onPlayerDrop(PlayerDropItemEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         if (!a.getSettings().getBoolSetting(ArenaSetting.ITEM_DROP)) {
            event.setCancelled(true);
         }
      }
   }
   
   @EventHandler
   public void onPlayerPickupItem(PlayerPickupItemEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         if (!a.getSettings().getBoolSetting(ArenaSetting.BLOCK_PLACE)) {
            event.setCancelled(true);
         }
      }
   }
   
   @EventHandler
   public void onPluginDisable(PluginDisableEvent event) {
      for (PlayerData data : PlayerManager.getAllPlayers()) {
         data.a.getPlayerManager().stopPlayer(data);
      }
   }
   
   /*
    * Broken for now D:
    * 
    * @EventHandler public void onEntityDBE(EntityDamageByEntityEvent event) {
    * if (event.getDamager() instanceof Player &&
    * !PlayerManager.playerInArena((Player) event.getDamager())) {
    * event.setCancelled(true); } if (event.getDamager() instanceof Projectile
    * && ((Projectile) event.getDamager()).getShooter() instanceof Player &&
    * !PlayerManager.playerInArena(((Player) ((Projectile)
    * event.getDamager()).getShooter()))) { event.setCancelled(true); } }
    */
   
   @EventHandler
   public void onHealthRegen(EntityRegainHealthEvent event) {
      if (event.getEntity() instanceof Player) {
         Player p = (Player) event.getEntity();
         if (PlayerManager.playerInArena(p)) {
            Arena a = PlayerManager.getPlayerArena(p);
            if (!a.getSettings().getBoolSetting(ArenaSetting.HEALTH_REGEN)) {
               if (event.getRegainReason() == RegainReason.REGEN) event.setCancelled(true);
            }
         }
      }
   }
   
   @EventHandler
   public void onFoodLoss(FoodLevelChangeEvent event) {
      if (event.getEntity() instanceof Player) {
         Player p = (Player) event.getEntity();
         if (PlayerManager.playerInArena(p)) {
            Arena a = PlayerManager.getPlayerArena(p);
            if (!a.getSettings().getBoolSetting(ArenaSetting.FOOD_LOSS)) {
               event.setCancelled(true);
            }
         }
      }
   }
   
   @EventHandler
   public void onEntityDBE(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player p = (Player) event.getEntity();
         if (!PlayerManager.playerInArena(p)) {
            return;
         }
         if (event.getCause() == DamageCause.FALL) {
            event.setCancelled(true);
            return;
         }
         if (p.getHealth() - event.getDamage() < 1) {
            Arena a = PlayerManager.getPlayerArena(p);
            if (a.hasStarted()) {
               a.getPlayerManager().getPlayer(p).removeLife(DeathCause.PLAYER);
            } else {
               p.teleport(a.getRegion().getWarp(WarpType.LOBBY));
            }
         }
      }
   }
   
   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      Player p = event.getPlayer();
      if (PlayerManager.playerInArena(p)) {
         PlayerManager man = PlayerManager.getPlayerArena(p).getPlayerManager();
         man.stopPlayer(man.getPlayer(event.getPlayer()));
      }
   }
   
}

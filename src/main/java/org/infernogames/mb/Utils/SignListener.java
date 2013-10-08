package org.infernogames.mb.Utils;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Commands.CommandJoin;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.PlayerManager;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class SignListener implements Listener {
   @EventHandler
   public void onSignChange(SignChangeEvent event) {
      if (event.getLine(0).equalsIgnoreCase("Minecraft Brawl")) {
         Player p = event.getPlayer();
         if (p.hasPermission("mb.signs")) {
            if (MBPlugin.arenaManager.arenaRegistered(event.getLine(1))) {
               event.setLine(0, "Minecraft Brawl");
               Arena arena = MBPlugin.arenaManager.getArena(event.getLine(1));
               FileManager man = new FileManager("Arenas");
               if (event.getLine(2).equalsIgnoreCase("join")) {
                  event.setLine(3, ChatColor.GREEN + arena.getName());
                  event.setLine(1, ChatColor.AQUA + "Click to Join");
                  // man... Lol
                  man.getConfig().set(get(arena, event.getBlock()), "join");
                  man.saveConfig();
               } else if (event.getLine(2).equalsIgnoreCase("status")) {
                  event.setLine(3, ChatColor.GREEN + arena.getName());
                  event.setLine(1, ChatColor.AQUA + "Blank Status");
                  man.getConfig().set(get(arena, event.getBlock()), "status");
                  man.saveConfig();
               } else {
                  Msg.warning(p, "The type '" + event.getLine(2) + "' doesn't exist!");
                  event.setCancelled(true);
                  return;
               }
               event.setLine(2, "");
            } else {
               Msg.warning(p, "The arena doesn't exist!");
               event.setCancelled(true);
            }
         } else {
            Msg.warning(p, "You don't have permission to make signs!");
            event.setCancelled(true);
         }
      }
   }
   
   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      if (event.getBlock().getState() instanceof Sign) {
         Sign sign = (Sign) event.getBlock().getState();
         if (sign.getLine(0) == "Minecraft Brawl") {
            if (MBPlugin.arenaManager.arenaRegistered(ChatColor.stripColor(sign.getLine(3)))) {
               FileManager man = new FileManager("Arenas");
               Arena a = MBPlugin.arenaManager.getArena(ChatColor.stripColor(sign.getLine(3)));
               if (man.getConfig().isSet(get(a, event.getBlock()))) {
                  man.getConfig().set(get(a, event.getBlock()), null);
                  man.saveConfig();
               } else {
                  Msg.warning(event.getPlayer(), "This sign doesn't exist!");
               }
            }
         }
      }
   }
   
   @EventHandler
   public void onPlayerInteract(final PlayerInteractEvent event) {
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
         Sign sign = (Sign) event.getClickedBlock().getState();
         if (sign.getLine(0).equals("Minecraft Brawl")) {
            if (MBPlugin.arenaManager.arenaRegistered(ChatColor.stripColor(sign.getLine(3)))) {
               FileManager man = new FileManager("Arenas");
               Arena a = MBPlugin.arenaManager.getArena(ChatColor.stripColor(sign.getLine(3)));
               if (man.getConfig().isSet(get(a, event.getClickedBlock()))) {
                  String type = man.getConfig().getString(get(a, event.getClickedBlock()));
                  if (type.equals("join")) {
                     if (CommandJoin.basicArenaChecks(event.getPlayer(), a.getName())) {
                        PlayerManager.openMenu(event.getPlayer(), a);
                     }
                  } else if (type.equals("status")) {
                     return;
                  } else {
                     Msg.warning(event.getPlayer(), "Bugged sign type! Contact a owner!");
                  }
               } else {
                  Msg.warning(event.getPlayer(), "This sign doesn't exist!");
               }
            } else {
               Msg.warning(event.getPlayer(), "This arena doesn't exist!");
            }
         }
      }
   }
   
   private String get(Arena a, Block b) {
      return "Arenas." + a.getName() + ".Signs." + LocationUtils.fromLocation(b);
   }
}

package org.infernogames.mb.Utils;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Commands.CommandJoin;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.PlayerManager;

public class SignListener implements Listener {
   @EventHandler
   public void onSignChange(SignChangeEvent event) {
      if (event.getLine(0).equalsIgnoreCase("Minecraft Brawl")) {
         Player p = event.getPlayer();
         if (p.hasPermission("mb.signs")) {
            if (ArenaManager.arenaRegistered(event.getLine(1))) {
               event.setLine(0, "Minecraft Brawl");
               Arena arena = ArenaManager.getArena(event.getLine(1));
               FileManager man = new FileManager("Arenas");
               String world = event.getBlock().getWorld().getName();
               int x = event.getBlock().getX();
               int y = event.getBlock().getY();
               int z = event.getBlock().getZ();
               event.setLine(2, "");
               if (event.getLine(2).equalsIgnoreCase("join")) {
                  event.setLine(3, ChatColor.GREEN + arena.getName());
                  event.setLine(1, ChatColor.AQUA + "Click to Join");
                  man.getConfig().set(
                           "Arenas." + arena.getName() + ".Signs." + world + ";" + x + ";" + y + ";" + z, "join");
                  man.saveConfig();
               } else if (event.getLine(2).equalsIgnoreCase("status")) {
                  event.setLine(3, ChatColor.GREEN + arena.getName());
                  event.setLine(1, ChatColor.AQUA + "Blank Status");
                  man.getConfig()
                           .set("Arenas." + arena.getName() + ".Signs." + world + ";" + x + ";" + y + ";" + z,
                                    "status");
                  man.saveConfig();
               } else {
                  Msg.warning(p, "That type of sign doesn't exist!");
                  event.setCancelled(true);
                  return;
               }
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
            if (ArenaManager.arenaRegistered(ChatColor.stripColor(sign.getLine(3)))) {
               FileManager man = new FileManager("Arenas");
               Arena a = ArenaManager.getArena(ChatColor.stripColor(sign.getLine(3)));
               String world = event.getBlock().getWorld().getName();
               int x = event.getBlock().getX();
               int y = event.getBlock().getY();
               int z = event.getBlock().getZ();
               if (man.getConfig()
                        .isSet("Arenas." + a.getName() + ".Signs." + world + ";" + x + ";" + y + ";" + z)) {
                  man.getConfig().set("Arenas." + a.getName() + ".Signs." + world + ";" + x + ";" + y + ";" + z,
                           null);
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
            if (ArenaManager.arenaRegistered(ChatColor.stripColor(sign.getLine(3)))) {
               FileManager man = new FileManager("Arenas");
               Arena a = ArenaManager.getArena(ChatColor.stripColor(sign.getLine(3)));
               String world = event.getClickedBlock().getWorld().getName();
               int x = event.getClickedBlock().getX();
               int y = event.getClickedBlock().getY();
               int z = event.getClickedBlock().getZ();
               if (man.getConfig()
                        .isSet("Arenas." + a.getName() + ".Signs." + world + ";" + x + ";" + y + ";" + z)) {
                  String type = man.getConfig().getString(
                           "Arenas." + a.getName() + ".Signs." + world + ";" + x + ";" + y + ";" + z);
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
}

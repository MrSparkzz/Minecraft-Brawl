package org.infernogames.mb.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.infernogames.mb.MBClass;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.ClassManager;
import org.infernogames.mb.Utils.IconMenu;
import org.infernogames.mb.Utils.IconMenu.Row;
import org.infernogames.mb.Utils.IconMenu.onClick;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         This class allows you to join a arena
 */
public class CommandJoin extends MBCommand {
   
   public CommandJoin() {
      super("join", 1, new Permission("mb.arena.join", "Joins an arena", PermissionDefault.TRUE));
   }
   
   @Override
   public void onCommand(final Player p, final String[] args) {
      if (basicArenaChecks(p, args[0])) {
         // Have him join arena
         final Arena a = MBPlugin.arenaManager.getArena(args[0]);
         IconMenu menu = new IconMenu("Pick your class!", (ClassManager.classAmount() / 9) + 1, new onClick() {
            @Override
            public boolean click(Player clicker, IconMenu menu, Row row, int slot, ItemStack item) {
               if (item == null || item.getType() == Material.AIR) {
                  return true;
               }
               a.getPlayerManager().startPlayer(p, a,
                        ClassManager.getRegisteredClass(item.getItemMeta().getDisplayName()));
               return false;
            }
         });
         for (MBClass c : ClassManager.getClasses()) {
            menu.addDynamicButton(new ItemStack(c.icon()), ChatColor.GREEN + c.name(), c.desc());
         }
         menu.open(p);
      }
   }
   
   public static boolean basicArenaChecks(Player p, String arena) {
      if (MBPlugin.arenaManager.arenaRegistered(arena)) {
         Arena a = MBPlugin.arenaManager.getArena(arena);
         if (!a.getPlayerManager().hasPlayer(p)) {
            if (p.hasPermission("scb.arena.join." + arena)) {
               if (!MBPlugin.arenaManager.getArena(arena).hasStarted()) {
                  return true;
               } else {
                  Msg.warning(p, "That arena has already started!");
               }
            } else {
               Msg.warning(p, "You do not have permission to join that arena!");
            }
         } else {
            Msg.warning(p, "You are already in an arena!");
         }
      } else {
         Msg.warning(p, "That arena does not exist!");
      }
      return false;
   }
}

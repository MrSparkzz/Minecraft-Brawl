package org.infernogames.mb.Commands;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.infernogames.mb.MBClass;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.ClassManager;
import org.infernogames.mb.Utils.IconMenu;
import org.infernogames.mb.Utils.IconMenu.Row;
import org.infernogames.mb.Utils.IconMenu.onClick;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class CommandJoin extends MBCommand {
   
   public CommandJoin() {
      super("join", "scb.arena.join", 1);
   }
   
   @Override
   public void onCommand(final Player p, final String[] args) {
      if (basicArenaChecks(p, args[0])) {
         // Have him join arena
         final Arena a = ArenaManager.getArena(args[0]);
         IconMenu menu = new IconMenu("Pick your class!", (ClassManager.classAmount() / 9) + 1, new onClick() {
            @Override
            public boolean click(Player clicker, IconMenu menu, Row row, int slot, ItemStack item) {
               p.sendMessage("You choose " + item.getItemMeta().getDisplayName());
               a.getManager().getPlayerManager()
                        .startPlayer(p, a, ClassManager.getRegisteredClass(item.getItemMeta().getDisplayName()));
               return false;
            }
         });
         for (MBClass c : ClassManager.getClasses()) {
            menu.addDynamicButton(new ItemStack(c.icon()), ChatColor.GREEN + c.name(), c.desc());
         }
         menu.open(p);
      }
   }
   
   private boolean basicArenaChecks(Player p, String arena) {
      if (ArenaManager.arenaRegistered(arena)) {
         Arena a = ArenaManager.getArena(arena);
         if (!a.getManager().getPlayerManager().hasPlayer(p)) {
            if (p.hasPermission("scb.arena.join." + arena)) {
               if (!ArenaManager.getArena(arena).getManager().hasStarted()) {
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

package net.supersmashcraft.Commands;

import net.supersmashcraft.IconMenu;
import net.supersmashcraft.IconMenu.Row;
import net.supersmashcraft.IconMenu.onClick;
import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.ClassUtils.JoinUtils;
import net.supersmashcraft.Classes.SSCClass;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.ClassManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class CommandJoin extends SSCCommand {
   
   public CommandJoin() {
      super("join", "scb.arena.join", 1);
   }
   
   @Override
   public void onCommand(final Player p, final String[] args) {
      if (JoinUtils.basicArenaChecks(p, args[0])) {
         // Have him join arena
         // JoinUtils.startPlayer(p, ArenaManager.getArena(args[0]),
         // ClassManager.getRegisteredClass(args[1]));
         final Arena a = ArenaManager.getArena(args[0]);
         IconMenu menu = new IconMenu("Pick your class!", (ClassManager.classAmount() / 9) + 1, new onClick() {
            @Override
            public boolean click(Player clicker, IconMenu menu, Row row, int slot, ItemStack item) {
               JoinUtils.startPlayer(p, a, ClassManager.getRegisteredClass(item.getItemMeta().getDisplayName()));
               return false;
            }
         });
         for (SSCClass c : ClassManager.getClasses()) {
            menu.addDynamicButton(new ItemStack(c.icon()), ChatColor.GREEN + c.name(), c.desc());
         }
         menu.open(p);
      }
   }
   
}

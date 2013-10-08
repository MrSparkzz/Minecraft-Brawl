package org.infernogames.mb.Commands;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Utils.LocationUtils;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         This class allows you to modify the arena
 */
public class CommandModify extends MBCommand {
   
   public CommandModify() {
      super("modify", -1, new Permission("mb.arena.modify", "Modifies an arena", PermissionDefault.OP));
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (args.length > 1) {
         if (MBPlugin.arenaManager.arenaRegistered(args[0])) {
            Arena a = MBPlugin.arenaManager.getArena(args[0]);
            if (args[1].equalsIgnoreCase("name")) {
               if (args.length == 3) {
                  FileManager man = new FileManager("Arenas");
                  man.getConfig().set("Arenas." + a.getName() + ".Name", args[2]);
                  Msg.msg(p, "Set arena name to " + args[2] + "!");
                  man.saveConfig();
               } else {
                  Msg.warning(p, "Not enough arguments!");
               }
            } else if (args[1].equalsIgnoreCase("clearspawns")) {
               Msg.msg(p, "Deleted all spawns! Make sure to add some");
            } else if (args[1].equalsIgnoreCase("addspawn")) {
               FileManager man = new FileManager("Arenas");
               int size = man.getConfig().getConfigurationSection("Arenas." + a.getName() + ".Spawns")
                        .getKeys(false).size();
               man.getConfig().set("Arenas." + a.getName() + "." + size,
                        LocationUtils.fromLocation(p.getLocation(), true, true));
               man.saveConfig();
            }
         } else {
            Msg.warning(p, "That arena does not exist!");
         }
      } else {
         Msg.warning(p, "You do not have enough arguments! /ssc modify <arena> <modifiers>");
      }
   }
   
}

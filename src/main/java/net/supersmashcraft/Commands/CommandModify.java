package net.supersmashcraft.Commands;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.ClassUtils.LocationUtils;
import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.FileManager;

import org.bukkit.entity.Player;

public class CommandModify extends SSCCommand {
   
   public CommandModify() {
      super("modify", "ssc.arena.modify", -1);
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (args.length > 1) {
         if (ArenaManager.arenaRegistered(args[0])) {
            Arena a = ArenaManager.getArena(args[0]);
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
                        LocationUtils.fromLocation(p.getLocation(), true));
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

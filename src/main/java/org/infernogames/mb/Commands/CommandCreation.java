package org.infernogames.mb.Commands;


import org.bukkit.entity.Player;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.CreationManager;
import org.infernogames.mb.Managers.CreationManager.Reward;
import org.infernogames.mb.Managers.CreationManager.Reward.RewardType;
import org.infernogames.mb.Utils.Msg;

public class CommandCreation extends MBCommand {
   
   public CommandCreation() {
      super("creation", "ssc.arena.creation", -1);
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (args.length == 0) {
         Msg.warning(p, "You have no arguments! Do /ssc creation start to start!");
         return;
      }
      
      if (args[0].equalsIgnoreCase("start")) {
         if (!CreationManager.playerStarted(p)) {
            CreationManager.addPlayer(p);
            Msg.msg(p, "You have started arena creation!");
         } else {
            Msg.warning(p, "You are in the process of making an arena! Do /arena scrap to scrap the arena.");
         }
      } else if (args[0].equalsIgnoreCase("name")) {
         if (args.length == 2) {
            if (CreationManager.playerStarted(p)) {
               CreationManager.getHolder(p).name = args[1];
               Msg.msg(p, "You have set the name to: " + args[1]);
            } else {
               Msg.warning(p, "You haven't started! Do /ssc creation start !");
            }
         } else {
            Msg.warning(p, "You need to define a name! /ssc creation name <name>");
         }
      } else if (args[0].equalsIgnoreCase("setpoint")) {
         if (args.length == 2) {
            if (args[1].equals("1")) {
               if (CreationManager.playerStarted(p)) {
                  CreationManager.getHolder(p).l1 = p.getLocation();
                  Msg.msg(p, "You set point 1 to your current position!");
               } else {
                  Msg.warning(p, "You haven't started! Do /ssc creation start !");
               }
            } else if (args[1].equals("2")) {
               if (CreationManager.playerStarted(p)) {
                  CreationManager.getHolder(p).l2 = p.getLocation();
                  Msg.msg(p, "You set point 2 to your current position!");
               } else {
                  Msg.warning(p, "You haven't started! Do /ssc creation start !");
               }
            } else {
               Msg.warning(p, "Invalid point! Do 1 or 2!");
            }
         } else {
            Msg.warning(p, "You need to define a position! Do 1 or 2!");
         }
      } else if (args[0].equalsIgnoreCase("setlobby")) {
         if (CreationManager.playerStarted(p)) {
            CreationManager.getHolder(p).lobby = p.getLocation();
            Msg.msg(p, "You set the warp at your current location!");
         } else {
            Msg.warning(p, "You haven't started! Do /ssc creation start !");
         }
      }
      
      else if (args[0].equalsIgnoreCase("addspawn")) {
         if (CreationManager.playerStarted(p)) {
            CreationManager.getHolder(p).spawns.add(p.getLocation());
            Msg.msg(p, "You added a spawn at your current location!");
         } else {
            Msg.warning(p, "You haven't started! Do /ssc creation start !");
         }
      } else if (args[0].equalsIgnoreCase("clearspawns")) {
         if (CreationManager.playerStarted(p)) {
            CreationManager.getHolder(p).spawns.clear();
            Msg.msg(p, "You cleared all the spawns!");
         } else {
            Msg.warning(p, "You haven't started! Do /ssc creation start !");
         }
      } else if (args[0].equalsIgnoreCase("setend")) {
         if (CreationManager.playerStarted(p)) {
            CreationManager.getHolder(p).end = p.getLocation();
            Msg.msg(p, "You set the end at your current location!");
         } else {
            Msg.warning(p, "You haven't started! Do /ssc creation start !");
         }
      } else if (args[0].equalsIgnoreCase("reward")) {
         if (CreationManager.playerStarted(p)) {
            if (args.length == 2) {
               CreationManager.getHolder(p).reward = new Reward(RewardType.Cash, Integer.parseInt(args[1]));
               Msg.msg(p, "You set the reward to $" + args[1] + "!");
            } else {
               if (p.getItemInHand() != null) {
                  CreationManager.getHolder(p).reward = new Reward(RewardType.Item, p.getItemInHand());
                  Msg.msg(p, "You set the reward to the item in your hand!");
               } else {
                  Msg.warning(p, "You can't set the item to nothing!");
               }
            }
         } else {
            Msg.warning(p, "You haven't started! Do /ssc creation start !");
         }
      } else if (args[0].equalsIgnoreCase("finish")) {
         String name = CreationManager.getHolder(p).name;
         String check = CreationManager.getHolder(p).check();
         if (check == null) {
            Msg.msg(p, "You have finished! Saving now.. if there is an error, try restarting your server.");
            CreationManager.getHolder(p).save();
            try {
               ArenaManager.addArena(CreationManager.createArena("Arenas." + name));
            } catch (NullPointerException e) {
               Msg.warning(p, "Yep we had an error! Try restarting/reloading your server.");
            }
         } else {
            Msg.warning(p, check);
         }
      } else {
         Msg.warning(p, "Argument does not exist! Try /ssc creation start");
      }
   }
   
}

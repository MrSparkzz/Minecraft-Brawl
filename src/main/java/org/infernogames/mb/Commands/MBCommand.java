package org.infernogames.mb.Commands;

import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public abstract class MBCommand {
   
   private String command;
   private String permission;
   private int args;
   
   public MBCommand(final String command, final String permission, int args) {
      this.command = command;
      this.permission = permission;
      this.args = args;
   }
   
   public abstract void onCommand(Player p, String[] args);
   
   public String getCommand() {
      return this.command;
   }
   
   public String getPermission() {
      return this.permission;
   }
   
   public int getArgumentLength() {
      return this.args;
   }
   
}

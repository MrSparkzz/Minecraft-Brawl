package org.infernogames.mb.Commands;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

/**
 * 
 * @author Paul, Breezeyboy,
 * 
 *         Basic class that all subcommands extend.
 */
public abstract class MBCommand {
   
   private String command;
   private Permission permission;
   private int args;
   
   public MBCommand(final String command, int args, final Permission permission) {
      this.command = command;
      this.permission = permission;
      this.args = args;
   }
   
   public abstract void onCommand(Player p, String[] args);
   
   public String getCommand() {
      return this.command;
   }
   
   public String getUsage() {
      return permission.getDescription();
   }
   
   public String getPermissionString() {
      return permission.getName();
   }
   
   public Permission getPermission() {
      return permission;
   }
   
   public int getArgumentLength() {
      return this.args;
   }
   
}

package net.supersmashcraft.ClassUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class Msg {
   
   private static String prefix = "&7[&aSSB&7]&e ";
   
   public static void msg(final Player player, final String msg) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
   }
   
   public static void warning(final Player player, final String msg) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4" + msg));
   }
}

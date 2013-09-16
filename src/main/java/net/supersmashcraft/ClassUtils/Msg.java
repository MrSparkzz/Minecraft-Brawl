package net.supersmashcraft.ClassUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Msg {
   
   private static String prefix = "&7[&aSSB&7]&e ";
   
   public static void msg(Player p, String msg) {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
   }
   
   public static void warning(Player p, String msg) {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4" + msg));
   }
   
}

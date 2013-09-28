package org.infernogames.mb.Managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.infernogames.mb.ItemHandler;
import org.infernogames.mb.MBClass;

public class ClassManager {
   
   private static List<MBClass> classes = new ArrayList<MBClass>();
   
   public static void registerClass(MBClass c) {
      classes.add(c);
   }
   
   public static int classAmount() {
      return classes.size();
   }
   
   public static List<MBClass> getClasses() {
      return classes;
   }
   
   public static void loadClasses() {
      File file = new File(FileManager.dataFolder + File.separator + "Classes");
      if (!file.exists()) {
         return;
      }
      for (File fi : file.listFiles()) {
         if (fi.getName().toLowerCase().contains(".yml")) {
            FileConfiguration c = YamlConfiguration.loadConfiguration(fi);
            Bukkit.getLogger().info("Loaded class: " + c.getString("Name", "Class"));
            @SuppressWarnings("deprecation")
            MBClass cl = new MBClass(c.getString("Name", "Class"), c.getString("Description", "Some Description"),
                     Material.getMaterial(c.getInt("Icon", 1)));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Boots", "type=0")));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Helmet", "type=0")));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Chestplate", "type=0")));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Leggings", "type=0")));
            for (Object o : c.getList("Items")) {
               ItemStack i = new ItemStack(Material.getMaterial(o.toString()), 1);
               i.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
               cl.addItem(i);
            }
            if (c.isSet("Abilities")) {
               for (Object s : c.getList("Abilities")) {
                  try {
                     if (s.toString().contains(";")) {
                        String[] args = s.toString().split(";");
                        String[] newArgs = new String[args.length - 1];
                        String name = args[0];
                        for (int i = 1; i < args.length; i++) {
                           newArgs[i - 1] = args[i];
                        }
                        cl.addAbility(AbilityManager.getAbility(name), newArgs);
                     } else {
                        cl.addAbility(AbilityManager.getAbility(s.toString()), new String[0]);
                     }
                  } catch (Exception e) {
                     Bukkit.getLogger().severe("Invalid ability " + s + " for " + c.getString("Name", "Class"));
                  }
               }
            }
            registerClass(cl);
         }
      }
   }
   
   public static boolean classExists(String name) {
      for (MBClass c : classes) {
         if (c.name().equalsIgnoreCase(name)) {
            return true;
         }
      }
      return false;
   }
   
   public static MBClass getRegisteredClass(String name) {
      for (MBClass c : classes) {
         if (c.name().equalsIgnoreCase(ChatColor.stripColor(name))) {
            return c;
         }
      }
      return null;
   }
   
}

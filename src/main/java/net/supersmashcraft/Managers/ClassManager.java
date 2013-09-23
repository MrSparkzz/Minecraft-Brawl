package net.supersmashcraft.Managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.supersmashcraft.ItemHandler;
import net.supersmashcraft.Classes.SSCClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ClassManager {
   
   private static List<SSCClass> classes = new ArrayList<SSCClass>();
   
   public static void registerClass(SSCClass c) {
      classes.add(c);
   }
   
   public static int classAmount() {
      return classes.size();
   }
   
   public static List<SSCClass> getClasses() {
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
            Bukkit.getLogger().info("Loaded class: " + c.getString("Name"));
            @SuppressWarnings("deprecation")
            SSCClass cl = new SSCClass(c.getString("Name", "Class"),
                     c.getString("Description", "Some Description"), Material.getMaterial(c.getInt("Icon", 1)));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Helmet", "type=0")));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Chestplate", "type=0")));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Leggings", "type=0")));
            cl.addItem(ItemHandler.fromString(c.getString("Armor.Boots", "type=0")));
            for (Object o : c.getList("Items")) {
               ItemStack i = new ItemStack(Material.getMaterial(o.toString()), 1);
               i.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
               cl.addItem(i);
            }
            registerClass(cl);
         }
      }
   }
   
   public static boolean classExists(String name) {
      for (SSCClass c : classes) {
         if (c.name().equalsIgnoreCase(name)) {
            return true;
         }
      }
      return false;
   }
   
   public static SSCClass getRegisteredClass(String name) {
      for (SSCClass c : classes) {
         if (c.name().equalsIgnoreCase(ChatColor.stripColor(name))) {
            return c;
         }
      }
      return null;
   }
   
}

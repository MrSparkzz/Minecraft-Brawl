package net.supersmashcraft.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Classes.SSCClass;

public class ClassManager {
   
   private static List<SSCClass> classes = new ArrayList<SSCClass>();
   
   public static void registerClass(SSCClass c) {
      classes.add(c);
      if(c instanceof Listener){
         Bukkit.getPluginManager().registerEvents((Listener) c, SSCPlugin.instance);
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
         if (c.name().equalsIgnoreCase(name)) {
            return c;
         }
      }
      return null;
   }
   
}

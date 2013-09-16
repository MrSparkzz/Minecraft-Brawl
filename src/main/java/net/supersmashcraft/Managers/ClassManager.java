package net.supersmashcraft.Managers;

import java.util.ArrayList;
import java.util.List;

import net.supersmashcraft.Classes.SSCClass;

public class ClassManager {
   
   private static List<SSCClass> classes = new ArrayList<SSCClass>();
   
   public static void registerClass(SSCClass c) {
      classes.add(c);
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

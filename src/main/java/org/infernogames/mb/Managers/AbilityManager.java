package org.infernogames.mb.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Abilities.MBAbility;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         This handles all registered abilities. Possibly will be removed in
 *         the future.
 */
public class AbilityManager {
   private static List<MBAbility> abilities = new ArrayList<MBAbility>();
   
   public static MBAbility getAbility(String name) {
      for (MBAbility ab : abilities) {
         if (ab.name().equalsIgnoreCase(name)) {
            return ab;
         }
      }
      return null;
   }
   
   public static boolean abilityRegistered(String name) {
      for (MBAbility ab : abilities) {
         if (ab.name().equalsIgnoreCase(name)) {
            return true;
         }
      }
      return false;
   }
   
   public static void registerAbility(MBAbility ab) {
      abilities.add(ab);
      if (ab instanceof Listener) {
         MBPlugin.registerListener((Listener) ab);
      }
   }
}

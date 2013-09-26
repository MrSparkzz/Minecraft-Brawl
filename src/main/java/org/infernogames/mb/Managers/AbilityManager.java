package org.infernogames.mb.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.infernogames.mb.Abilities.MBAbility;

public class AbilityManager {
   private static List<MBAbility> abilities = new ArrayList<MBAbility>();
   
   public static MBAbility getAbility(String name){
      for(MBAbility ab : abilities){
         if(ab.name().equalsIgnoreCase(name)){
            return ab;
         }
      }
      return null;
   }
   
   public static boolean abilityRegistered(String name){
      for(MBAbility ab : abilities){
         if(ab.name().equalsIgnoreCase(name)){
            return true;
         }
      }
      return false;
   }
   
   public static void registerAbility(MBAbility ab){
      abilities.add(ab);
      Bukkit.getLogger().info("Registerd Ability: " + ab.name());
   }
}

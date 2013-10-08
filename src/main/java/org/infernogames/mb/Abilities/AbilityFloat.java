package org.infernogames.mb.Abilities;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Used to decide how many times a player can float
 */
public class AbilityFloat extends MBAbility {
   private int floatTimes = 4;
   
   public AbilityFloat() {
      super("Float");
   }
   
   public int getFloatTimes() {
      return floatTimes;
   }
}

package net.supersmashcraft.Abilities;

public class AbilityFloat extends SSCAbility {
   private int floatTimes;
   
   public AbilityFloat(int floatTimes) {
      super("Float");
      this.floatTimes = floatTimes;
   }
   
   public int getFloatTimes() {
      return floatTimes;
   }
}

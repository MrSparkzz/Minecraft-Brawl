package org.infernogames.mb.Arena;

import org.infernogames.mb.Managers.FileManager;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Class used to store data about the arena. Stored in a integer, or a
 *         boolean.
 */
public class ArenaSettings {
   
   private FileManager man;
   private String path;
   
   public ArenaSettings(Arena arena) {
      man = new FileManager("Arenas");
      path = "Arenas." + arena.getName() + ".";
      for (ArenaSetting s : ArenaSetting.values()) {
         man.addDefault(path + "Settings." + s, s.getValue());
      }
      man.saveConfig();
   }
   
   public void setSetting(ArenaSetting setting, boolean b) {
      man.getConfig().set(path + "Settings." + setting, b);
   }
   
   public void setSetting(ArenaSetting setting, int i) {
      man.getConfig().set(path + "Settings." + setting, i);
   }
   
   public int getIntSetting(ArenaSetting setting) {
      return man.getConfig().getInt(path + "Settings." + setting);
   }
   
   public boolean getBoolSetting(ArenaSetting setting) {
      return man.getConfig().getBoolean(path + "Settings." + setting);
   }
   
   public enum ArenaSetting {
      MIN_PLAYERS(2), MAX_PLAYERS(4), EXIT_ARENA(false), AUTO_START_TIMER(5), DISABLE_AUTO_START(false), RESTORE_ARENA_ONFINISH(
               true), BLOCK_BREAK(false), BLOCK_PLACE(false), ITEM_DROP(false), INVENTORY_MOVE(false), PICKUP_ITEMS(
               false), STARTING_LIVES(8);
      
      private int i;
      private boolean b;
      
      private String type;
      
      private ArenaSetting(int d) {
         this.i = d;
         type = "int";
      }
      
      private ArenaSetting(boolean d) {
         this.b = d;
         type = "bool";
      }
      
      public int getInteger() {
         return i;
      }
      
      public boolean getBoolean() {
         return b;
      }
      
      public Object getValue() {
         switch (type) {
         case "int":
            return i;
         case "bool":
            return b;
         }
         return null;
      }
   }
}

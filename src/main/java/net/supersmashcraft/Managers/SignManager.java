package net.supersmashcraft.Managers;

import java.util.HashMap;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.ClassUtils.LocationUtils;

import org.bukkit.block.Block;

public class SignManager {
   
   public static void addSign(Arena a, Block b, SignType type) {
      FileManager man = new FileManager("Arenas");
      int size = 0;
      if (man.getConfig().isSet("Arenas." + a.getName() + ".Signs")) {
         size = man.getConfig().getConfigurationSection("Arenas." + a.getName() + ".Signs").getKeys(false).size();
      }
      man.getConfig().set("Arenas." + a.getName() + ".Signs." + size + ".Type", type.name());
      man.getConfig().set("Arenas." + a.getName() + ".Signs." + size + ".Loc",
               LocationUtils.fromLocation(b.getLocation(), true, false));
      man.saveConfig();
   }
   
   public static HashMap<Block, SignType> getForArena(Arena a) {
      HashMap<Block, SignType> map = new HashMap<Block, SignType>();
      FileManager man = new FileManager("Arenas");
      String path = "Arenas." + a.getName() + ".Signs";
      if (man.getConfig().isSet(path)) {
         for (String s : man.getConfig().getConfigurationSection(path).getKeys(false)) {
            Block b = LocationUtils.toLocation(man.getConfig().getString(path + "." + s + ".Loc")).getBlock();
            SignType type = SignType.valueOf(man.getConfig().getString(path + "." + s + ".Type"));
            map.put(b, type);
         }
      }
      return map;
   }
   
   public enum SignType {
      STATUS
   }
   
}

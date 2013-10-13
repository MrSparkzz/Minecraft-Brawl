package org.infernogames.mb.Utils;

import java.util.*;

import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;

public class StaggeredRunnable extends BukkitRunnable {
   private List<BlockState> hugeList, list = new ArrayList<BlockState>();
   
   public StaggeredRunnable(List<BlockState> hugeList) {
      this.hugeList = hugeList;
   }
   
   @Override
   public void run() {
      for (BlockState s : hugeList) {
         if (!s.getBlock().getState().getType().equals(s.getType())) {
            list.add(s);
         }
      }
      hugeList = null;
      MBPlugin.registerRepeatedRunnable(new StaggeredTask(), 0, (long) 0.1);
   }
   
   private class StaggeredTask extends BukkitRunnable {
      private int maxIterationsPerTick = 100, iteratorCount = 0;
      
      public StaggeredTask() {
         maxIterationsPerTick = list.size() / 3;
      }
      
      @Override
      public void run() {
         iteratorCount = 0;
         while (!list.isEmpty() && iteratorCount < maxIterationsPerTick) {
            BlockState s = list.get(0);
            s.update(true, false);
            list.remove(0);
            iteratorCount++;
         }
         if (list.isEmpty()) {
            cancel();
         }
      }
   }
}

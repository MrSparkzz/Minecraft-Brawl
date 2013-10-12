package org.infernogames.mb.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;

public class StaggeredRunnable extends BukkitRunnable {
   private List<BlockState> hugeList;
   private List<BlockState> newBlocks = new ArrayList<BlockState>();
   
   public StaggeredRunnable(List<BlockState> hugeList) {
      this.hugeList = hugeList;
   }
   
   @Override
   public void run() {
      for (BlockState s : hugeList) {
         if (!s.getBlock().getState().getType().equals(s.getType())) {
            newBlocks.add(s);
         }
      }
      hugeList = null;
      MBPlugin.registerRepeatedRunnable(new StaggeredTask(newBlocks), 0, (long) 0.1);
   }
   
   private class StaggeredTask extends BukkitRunnable {
      
      private List<BlockState> list;
      private int iteratorCount = 0;
      private int maxIterationsPerTick = 100;
      
      public StaggeredTask(List<BlockState> list) {
         this.list = list;
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
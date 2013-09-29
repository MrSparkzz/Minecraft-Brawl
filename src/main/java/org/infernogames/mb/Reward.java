package org.infernogames.mb;

import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class Reward {
   private RewardType type;
   private ItemStack reward;
   private int cash;
   
   public Reward(RewardType type, Object reward) {
      this.type = type;
      if (reward instanceof ItemStack) {
         this.reward = (ItemStack) reward;
      } else {
         cash = Integer.parseInt(reward.toString());
      }
   }
   
   public RewardType getType() {
      return this.type;
   }
   
   public ItemStack getReward() {
      return this.reward;
   }
   
   public int getCash() {
      return this.cash;
   }
   
   public enum RewardType {
      Cash, Item
   }
}
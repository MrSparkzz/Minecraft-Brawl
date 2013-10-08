package org.infernogames.mb;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.infernogames.mb.Managers.EcoManager;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Class used for rewards to give to players.
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
   
   public void give(Player p) {
      switch (type) {
      case Cash:
         try {
            EcoManager.giveMoney(p, cash);
         } catch (ClassNotFoundException e) {
            Msg.opBroadcast("We tried to give " + p.getName() + " $" + cash + " but Vault was not found!");
         }
         break;
      case Item:
         p.getInventory().addItem(reward);
         break;
      default:
         Msg.msg(p, "Some weird bug here.. please report it.");
         break;
      }
   }
   
   public enum RewardType {
      Cash, Item
   }
   
}
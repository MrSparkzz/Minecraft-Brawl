package org.infernogames.mb.Utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Damager {
   private Player p;
   
   public Damager(Player p) {
      this.p = p;
   }
   
   public void damage(double damage) {
      p.damage(damage);
   }
   
   public void damage(double damage, Player damager) {
      p.damage(damage, damager);
   }
   
   public void poison(int duration, Player poisoner) {
      p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, 1), true);
   }
   
   public void wither(int duration, Player poisoner) {
      p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, duration, 1), true);
   }
   
   public void heal(double hearts) {
      if (p.getHealth() + hearts > p.getMaxHealth()) {
         p.setHealth(p.getMaxHealth());
      } else {
         p.setHealth(p.getHealth() + hearts);
      }
   }
   
   public void explode(int radius, double playerDamage, double enemyDamage) {
      p.getWorld().createExplosion(p.getLocation(), 0.0F);
      for (Entity e : p.getNearbyEntities(radius, radius, radius))
         if (e instanceof Player)
            new Damager((Player) e).damage(enemyDamage);
      damage(playerDamage);
   }
   
   public void poison(int radius, int duration) {
      for (Entity e : p.getNearbyEntities(radius, radius, radius))
         if (e instanceof Player)
            new Damager((Player) e).poison(duration, p);
   }
   
   public void wither(int radius, int duration) {
      for (Entity e : p.getNearbyEntities(radius, radius, radius))
         if (e instanceof Player)
            new Damager((Player) e).wither(duration, p);
   }
}

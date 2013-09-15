package net.supersmashcraft.ClassUtils;

import javax.annotation.Nonnull;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DoubleJump implements Listener {

	public DoubleJump() {
		new BukkitRunnable() {
			public void run() {
				for (String pName : ArenaManager.getAllPlayers()) {
					Player player = Bukkit.getPlayerExact(pName);
					if (player.getExp() < 1.0f) {
						player.setExp(player.getExp() + 0.2f);
					} else if (player.getExp() > 1.0f) {
						player.setExp(1.0f);
					}
					refreshJump(player);
				}
			}
		}.runTaskTimer(SSCPlugin.instance, 0, 10);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void noFallDamage(final EntityDamageEvent event) {
		if (event.getCause() != DamageCause.FALL)
			return;
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if (ArenaManager.isPlayerInArena(player))
			return;

		event.setCancelled(true);
		player.addAttachment(SSCPlugin.instance, "doublejump.nofalldamage",
				false);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onGroundStateChanged(final PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR)
			return;
		if (!ArenaManager.isPlayerInArena(p))
			return;

		if (p.getExp() == 0.0f) {
			p.addAttachment(SSCPlugin.instance, "doublejump.nofalldamage", true);
			p.addAttachment(SSCPlugin.instance, "doublejump.using", false);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerToggleFlight(final PlayerToggleFlightEvent event) {
		if (!ArenaManager.isPlayerInArena(event.getPlayer()))
			return;
		Player player = event.getPlayer();
		if (event.isFlying()) {
			event.getPlayer().addAttachment(SSCPlugin.instance,
					"doublejump.using", true);
			event.getPlayer().setAllowFlight(false);
			player.setExp(0.0f);
			event.setCancelled(true);

			double pitch = Math.toRadians(player.getLocation().getPitch());
			double yaw = Math.toRadians(player.getLocation().getYaw());

			Vector normal = new Vector(-(Math.cos(pitch) * Math.sin(yaw)),
					-Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));

			normal.setY(0.75 + Math.abs(normal.getY()) * 0.5);
			event.getPlayer().setVelocity(normal);

			player.getWorld().playSound(player.getLocation(),
					Sound.ZOMBIE_INFECT, 0.5f, 1.8f);
		}
	}

	public void refreshJump(@Nonnull Player player) {
		if (player.getExp() >= 1.0f) {
			player.setAllowFlight(true);
		}
	}

}

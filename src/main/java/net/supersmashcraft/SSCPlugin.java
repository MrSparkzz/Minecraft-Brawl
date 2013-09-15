package net.supersmashcraft;

import net.supersmashcraft.ClassUtils.DoubleJump;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SSCPlugin extends JavaPlugin {

	public static SSCPlugin instance;

	@Override
	public void onEnable() {
		instance = this;

		registerListener(new DoubleJump());
	}

	private void registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}
}

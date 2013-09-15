package me.SSC;

import org.bukkit.plugin.java.JavaPlugin;

public class SSCPlugin extends JavaPlugin {

	public static SSCPlugin instance;

	@Override
	public void onEnable() {
		instance = this;
	}
}

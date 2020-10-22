/*
 * Cheran (21C)
 * 
 * Copyright (c) 2020 21C, Inc. All Rights Reserved.
 */
package com.twentyonec.ItemsLogger;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import com.twentyonec.ItemsLogger.utils.Config;
import com.twentyonec.ItemsLogger.utils.Storage;
/**
 * Plugin main class
 * 
 * @version 	0.0.1 22 October 2020
 * @author 		Cheran (21C)
 */
public class ItemsLogger extends JavaPlugin {

	static ItemsLogger plugin = null;
	Config config = null;
	Storage storage = null;

	@Override
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		config = new Config(getConfig());
		storage = Storage.getStorage(this);
		storage.setUpTable();
	}

	@Override
	public void onDisable() {

	}

	public static ItemsLogger getPlugin() {
		return plugin;
	}
	public Config getConfigManager() {
		return config;
	}

	public void debugMessage(String message) {
		if (getConfigManager().getDebug())
			plugin.getLogger().log(Level.INFO, "[DEBUG]: " + message);
	} 
}
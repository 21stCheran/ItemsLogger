/*
 * Cheran (21C)
 * 
 * Copyright (c) 2020 21C, Inc. All Rights Reserved.
 */
package com.twentyonec.ItemsLogger;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.twentyonec.ItemsLogger.listeners.DeathSave;
import com.twentyonec.ItemsLogger.utils.Config;
import com.twentyonec.ItemsLogger.utils.Storage;

/**
 * Plugin main class
 * 
 * @version 1.0.1 22 October 2020
 * @author Cheran (21C)
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

		this.getServer().getPluginManager().registerEvents(new DeathSave(), this);
	}

	@Override
	public void onDisable() {
		debugMessage("Attempting to save all player data");
		for (final Player player : Bukkit.getOnlinePlayers()) {
			final ItemPlayer itemPlayer = new ItemPlayer(player);
			itemPlayer.savePlayer();
		}
	}

	public static ItemsLogger getPlugin() {
		return plugin;
	}

	public Config getConfigManager() {
		return config;
	}

	public void debugMessage(final String message) {
		if (config.getDebug())
			plugin.getLogger().log(Level.INFO, "[DEBUG]: " + message);
	}
}
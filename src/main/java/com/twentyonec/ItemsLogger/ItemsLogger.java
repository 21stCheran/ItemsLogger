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

import com.twentyonec.ItemsLogger.commands.OpenItemLog;
import com.twentyonec.ItemsLogger.commands.ViewLogList;
import com.twentyonec.ItemsLogger.listeners.DeathSave;
import com.twentyonec.ItemsLogger.listeners.JoinSave;
import com.twentyonec.ItemsLogger.listeners.QuitSave;
import com.twentyonec.ItemsLogger.utils.Config;
import com.twentyonec.ItemsLogger.utils.Storage;

/**
 * Plugin main class
 * 
 * @version 1.0.1 20 Jan 2021
 * @author Cheran (21C)
 */
public class ItemsLogger extends JavaPlugin {

	private static ItemsLogger plugin = null;
	private Config config = null;
	private Storage storage = null;

	@Override
	public void onEnable() {
		ItemsLogger.plugin = this;
		this.saveDefaultConfig();
		this.config = new Config(getConfig());
		this.storage = Storage.getStorage(this);
		this.storage.setUpTable();
		this.storage.deleteLogs(this.config.getDeleteDays());
		this.loadClasses();
	}

	@Override
	public void onDisable() {
		if (this.config.getRestart()) {
			debugMessage("Attempting to log all player data");
			for (final Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("itemslogger.log")) {
					final ItemPlayer itemPlayer = new ItemPlayer(player, "Restart");
					itemPlayer.savePlayer();
				}
			}
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

	private void loadClasses() {
		debugMessage("Loading classes");
		if (config.getDeath()) {
			debugMessage("Registering Death Event");
			this.getServer().getPluginManager().registerEvents(new DeathSave(), this);
		}
		if (config.getJoin()) {
			debugMessage("Registering Join Event");
			this.getServer().getPluginManager().registerEvents(new JoinSave(), this);
		}
		if (config.getQuit()) {
			debugMessage("Registering Quit Event");
			this.getServer().getPluginManager().registerEvents(new QuitSave(), this);
		}
		this.getCommand("itemslogger").setExecutor(new ViewLogList());
		this.getCommand("openitemlog").setExecutor(new OpenItemLog());
	}
}
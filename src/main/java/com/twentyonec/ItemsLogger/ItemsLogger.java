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

import com.twentyonec.ItemsLogger.commands.Cmd;
import com.twentyonec.ItemsLogger.commands.MainCommand;
import com.twentyonec.ItemsLogger.commands.OpenItemLog;
import com.twentyonec.ItemsLogger.listeners.DeathSave;
import com.twentyonec.ItemsLogger.listeners.JoinSave;
import com.twentyonec.ItemsLogger.listeners.QuitSave;
import com.twentyonec.ItemsLogger.utils.Cause;
import com.twentyonec.ItemsLogger.utils.Config;
import com.twentyonec.ItemsLogger.utils.Permissions;
import com.twentyonec.ItemsLogger.utils.Storage;

/**
 * Plugin main class
 * 
 * @version 1.1.0 20 Jan 2021
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
		this.registerCommands();
		this.registerEvents();
	}

	@Override
	public void onDisable() {
		if (this.config.getRestart()) {
			debugMessage("Attempting to log all player data");
			for (final Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission(Permissions.PERMISSION_LOG)) {
					final ItemPlayer itemPlayer = new ItemPlayer(player, Cause.RESTART);
					this.storage.saveItemPlayer(itemPlayer);
				}
			}
		}
	}

	public void reload() {
		this.saveDefaultConfig();
		this.reloadConfig();
		this.config = new Config(getConfig());
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

	private void registerEvents() {
		debugMessage("Registering events.");
		this.getServer().getPluginManager().registerEvents(new DeathSave(), this);
		this.getServer().getPluginManager().registerEvents(new JoinSave(), this);
		this.getServer().getPluginManager().registerEvents(new QuitSave(), this);
	}

	private void registerCommands() {
		debugMessage("Registering commands.");
		this.getCommand(Cmd.ITEMSLOGGER).setExecutor(new MainCommand());
		this.getCommand(Cmd.OPENITEMSLOG).setExecutor(new OpenItemLog());
	}
}
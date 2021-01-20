package com.twentyonec.ItemsLogger.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private final FileConfiguration configuration;

	private final String DEBUG_PATH = "debug";

	private final String USERNAME_PATH = "mysql.username";
	private final String PASSWORD_PATH = "mysql.password";
	private final String HOSTNAME_PATH = "mysql.hostname";
	private final String PORT_PATH = "mysql.port";
	private final String DATABASE_PATH = "mysql.database";
	private final String DELETE_PATH = "mysql.delete";

	private final String STORAGE_DEATH = "inventoryStorageType.death.enabled";
	private final String STORAGE_RESTART = "inventoryStorageType.restart.enabled";

	public Config(final FileConfiguration configuration) {
		this.configuration = configuration;
	}

	public boolean getDebug() {
		return configuration.getBoolean(DEBUG_PATH);
	}

	public String getUsername() {
		return configuration.getString(USERNAME_PATH);
	}

	public String getPassword() {
		return configuration.getString(PASSWORD_PATH);
	}

	public String getHostname() {
		return configuration.getString(HOSTNAME_PATH);
	}

	public String getPort() {
		return configuration.getString(PORT_PATH);
	}

	public String getDatabase() {
		return configuration.getString(DATABASE_PATH);
	}
	
	public int getDeleteDays() {
		return configuration.getInt(DELETE_PATH);
	}

	public boolean getDeath() {
		return configuration.getBoolean(STORAGE_DEATH);
	}

	public boolean getRestart() {
		return configuration.getBoolean(STORAGE_RESTART);
	}

}
package com.twentyonec.ItemsLogger;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.twentyonec.ItemsLogger.utils.Storage;

public class ItemPlayer {

	ItemsLogger plugin = ItemsLogger.getPlugin();
	Storage storage = plugin.getStorageManager(); //change and check later

	private final UUID uuid;
	private final String inv;
	private final int x,y,z;
	private final int experience;
	private final String cause;
	private final Date date;
	private final Timestamp time;

	public ItemPlayer (final Player player, final String cause) {

		this.uuid = player.getUniqueId();
		this.inv = player.getInventory().getStorageContents().toString();
		this.x = player.getLocation().getBlockX();
		this.y = player.getLocation().getBlockY();
		this.z = player.getLocation().getBlockZ();
		this.experience = player.getTotalExperience();
		this.cause = cause;

		java.util.Date longDate = new java.util.Date();
		this.date = new Date(longDate.getTime());
		this.time = new Timestamp(longDate.getTime());

	}

	public ItemPlayer (final Player player) {

		this.uuid = player.getUniqueId();
		this.inv = player.getInventory().getStorageContents().toString();
		this.x = player.getLocation().getBlockX();
		this.y = player.getLocation().getBlockY();
		this.z = player.getLocation().getBlockZ();
		this.experience = player.getTotalExperience();
		this.cause = "Restart";

		java.util.Date longDate = new java.util.Date();
		this.date = new Date(longDate.getTime());
		this.time = new Timestamp(longDate.getTime());


	}

	public void savePlayer() {

		final String sql = "INSERT INTO itemslogger"
				+ "(uuid, inventory, cause, loc_x, loc_y, loc_z, experience, date, time) "
				+ "VALUES ('"+ uuid + "','" + inv + "','" + cause 
				+ "'," + x + "," + y + "," + z + "," 
				+ experience + ",?" + ",?" + ");";
		storage.update(sql, date, time);

	}

	public void loadPlayer() {
		//TODO
	}

}
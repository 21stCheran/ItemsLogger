package com.twentyonec.ItemsLogger;

import java.sql.Date;
import java.sql.Time;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.twentyonec.ItemsLogger.utils.Serialize;
import com.twentyonec.ItemsLogger.utils.Storage;

public class ItemPlayer {

	private ItemsLogger plugin = ItemsLogger.getPlugin();
	private Storage storage = Storage.getStorage(plugin);

	final UUID uuid;
	final String inv;
	final String cause;
	final int x, y, z;
	final int experience;
	public final Date date;
	public final Time time;

	public ItemPlayer(final Player player, final String cause) {
		final java.util.Date longDate = new java.util.Date();

		this.uuid = player.getUniqueId();
		this.inv = Serialize.itemSerialize(player.getInventory().getContents());
		this.x = player.getLocation().getBlockX();
		this.y = player.getLocation().getBlockY();
		this.z = player.getLocation().getBlockZ();
		this.experience = player.getTotalExperience();
		this.cause = cause;
		this.date = new Date(longDate.getTime());
		this.time = new Time(longDate.getTime());

	}

	public ItemPlayer(final Player player) {
		final java.util.Date longDate = new java.util.Date();

		this.uuid = player.getUniqueId();
		this.inv = Serialize.itemSerialize(player.getInventory().getContents());
		this.x = player.getLocation().getBlockX();
		this.y = player.getLocation().getBlockY();
		this.z = player.getLocation().getBlockZ();
		this.experience = player.getTotalExperience();
		this.cause = "Restart";
		this.date = new Date(longDate.getTime());
		this.time = new Time(longDate.getTime());

	}
	
	

	public ItemPlayer(UUID uuid, String inv, String cause, int x, int y, int z, int experience, Date date, Time time) {
		super();
		this.uuid = uuid;
		this.inv = inv;
		this.x = x;
		this.y = y;
		this.z = z;
		this.experience = experience;
		this.cause = cause;
		this.date = date;
		this.time = time;
	}

	public void savePlayer() {

		final String sql = "INSERT INTO itemslogger"
				+ "(uuid, inventory, cause, loc_x, loc_y, loc_z, experience, date, time) " 
				+ "VALUES ('" + uuid + "','" + inv + "','" 
				+ cause + "'," + x + "," + y + "," + z + "," 
				+ experience + ",?" + ",?" + ");";
		storage.update(sql, date, time);

	}

	public void loadPlayer() {
		// TODO
	}

	public void serialize() {

	}

}

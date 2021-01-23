package com.twentyonec.ItemsLogger;

import java.sql.Date;
import java.sql.Time;

import java.util.UUID;

import org.bukkit.entity.Player;


import com.twentyonec.ItemsLogger.utils.Cause;
import com.twentyonec.ItemsLogger.utils.Serialize;

public class ItemPlayer {

	private final UUID uuid;
	private final String inv;
	private final String cause;
	private final int x, y, z;
	private final int experience;
	private final Date date;
	private final Time time;

	public ItemPlayer(final Player player, Cause cause) {
		final java.util.Date longDate = new java.util.Date();

		this.uuid = player.getUniqueId();
		this.inv = Serialize.itemStackArrayToBase64(player.getInventory().getContents());
		this.x = player.getLocation().getBlockX();
		this.y = player.getLocation().getBlockY();
		this.z = player.getLocation().getBlockZ();
		this.experience = player.getTotalExperience();
		this.cause = cause.getCause();
		this.date = new Date(longDate.getTime());
		this.time = new Time(longDate.getTime());

	}

	public ItemPlayer(final UUID uuid, final String inv, final String cause, final int x, final int y, final int z, final int experience, final Date date, final Time time) {
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

	//getters
	public UUID getUUID() {
		return this.uuid;
	}
	public String getInventory() {
		return this.inv;
	}
	public String getCause() {
		return this.cause;
	}
	public String getLocation() {
		return this.x + ", " + this.y + ", " + this.z;
	}
	public String getDate() {
		return this.date.toString() + " " + this.time.toString();
	}
	public int getExperience() {
		return this.experience;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
	public Time getTime() {
		return time;
	}


}

package com.twentyonec.ItemsLogger;

import java.sql.Date;
import java.sql.Time;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.twentyonec.ItemsLogger.utils.Serialize;
import com.twentyonec.ItemsLogger.utils.Storage;

public class ItemPlayer {

	private final ItemsLogger plugin = ItemsLogger.getPlugin();
	private final Storage storage = Storage.getStorage(plugin);

	private UUID uuid;
	private String inv;
	private String cause;
	private int x, y, z;
	private int experience;
	private Date date;
	private Time time;

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

	public void savePlayer() {

		new BukkitRunnable() {
			@Override
			public void run() {
				final String sql = "INSERT INTO itemslogger"
						+ "(uuid, inventory, cause, loc_x, loc_y, loc_z, experience, date, time) " 
						+ "VALUES ('" + uuid + "','" + inv + "','" 
						+ cause + "'," + x + "," + y + "," + z + "," 
						+ experience + ",?" + ",?" + ");";
				storage.update(sql, date, time);
			}
		}.runTaskAsynchronously(plugin);


	}

	public void loadInventory(final Player sender) {

		final ItemStack[] items = Serialize.itemDeserialize(this.inv);
		final Inventory inv = Bukkit.createInventory(null, 54, 
				sender.getDisplayName() 
				+ " Death Inventory");
		inv.addItem(items);
		sender.openInventory(inv);
	}

	//getters
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


}

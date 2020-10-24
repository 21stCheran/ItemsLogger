package com.twentyonec.ItemsLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.twentyonec.ItemsLogger.utils.Storage;

public class ItemPlayer {

	ItemsLogger plugin = ItemsLogger.getPlugin();
	Storage storage = Storage.getStorage(plugin);

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

		//remove null fixer and serials later into serizlie.java
		final List<ItemStack> itemlist = Arrays
				.asList(player.getInventory().getContents())
				.stream()
				.filter(stack -> stack != null)
				.collect(Collectors.toList());
		ItemStack[] items = itemlist.toArray(new ItemStack[0]);

		for (ItemStack item : items) {
			HashMap<String, Object> map =  (HashMap<String, Object>) item.serialize(); 

			try {
				//serialize
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(out);
				objOut.writeObject(map);
				objOut.close();

				//deserialize
				ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
				HashMap<String, Object> actual = (HashMap<String, Object>) objIn.readObject();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
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

	public void serialize() {

	}

}

package com.twentyonec.ItemsLogger.utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;

public class CommandsUtil {

	private ItemsLogger plugin = ItemsLogger.getPlugin();
	private Storage storage;
	private int index;
	private UUID uuid;

	public CommandsUtil(UUID uuid) {

		this.storage = Storage.getStorage(plugin);
		this.index = 10;
		this.uuid = uuid;
	}

	public CommandsUtil(UUID uuid, int index) {

		this.storage = Storage.getStorage(plugin);
		this.index = index * 10;
		this.uuid = uuid;
	}

	public ItemPlayer retrieveData() {
		//returns all the player's logged data
		//TODO
		return null;
	}

	public PlayerData[] retrieveList() {

		String sql = " SELECT * FROM itemslogger WHERE uuid = '"
				+ uuid
				+ "' ORDER BY date DESC, time DESC";
		ResultSet rs = storage.query(sql);

		PlayerData[] playerDataArray = new PlayerData[10];
		try {
			int i = 0;
			while (rs.next()&&(i<10)) {
				String cause = rs.getString("cause");
				Date date = rs.getDate("date");
				Timestamp time = rs.getTimestamp("time");

				playerDataArray[i] = new PlayerData(cause, date, time);
				i++;
			}
			return playerDataArray;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] retrieveList(Date date) {
		return null;
	}

	public String[] retrieveList(String cause) {
		return null;
	}

	public String[] retrieveList(Date date, String cause) {
		return null;
	}

}

class PlayerData {

	String cause;
	Date date;
	Timestamp time;

	public PlayerData(String cause, Date date, Timestamp time) {
		this.cause = cause;
		this.date = date;
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "Cause: " + cause + " Date: " + date + time;
	}

}

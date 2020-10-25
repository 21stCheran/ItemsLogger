package com.twentyonec.ItemsLogger.utils;

import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;

public class Encoder {

	public static String serializeItems(ItemStack[] invItems) {

		Gson gson = new Gson();
		String[] serialarray = new String[invItems.length];
		
		for (int i = 0; i < serialarray.length; i++) {
			String temp = gson.toJson(invItems[i]);
			serialarray[i] = temp;
		}
		
		String serial = gson.toJson(serialarray);		
		System.out.println("SERIAL: " + serial);
		return serial;
	}

	public static ItemStack[] deserializeItems(String serial) {

		Gson gson = new Gson();
		String[] serialarray = gson.fromJson(serial, String[].class);
		
		ItemStack[] items = new ItemStack[serialarray.length];
		for (int i = 0; i < items.length; i++) {
			ItemStack temp = gson.fromJson(serialarray[i], ItemStack.class);
			items[i] = temp;
		}
		return items;
	}

}

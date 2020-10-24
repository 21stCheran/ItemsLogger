package com.twentyonec.ItemsLogger.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class Serialize {

	public static String itemSerialize(ItemStack[] invItems) {

		final List<ItemStack> itemlist = Arrays
				.asList(invItems)
				.stream()
				.filter(stack -> stack != null)
				.collect(Collectors.toList());
		
		ItemStack[] items = itemlist.toArray(new ItemStack[0]);

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeInt(items.length);

			for (ItemStack item : items) {
				HashMap<String, Object> map = (HashMap<String, Object>) item.serialize();
				objOut.writeObject(map);
			}

			objOut.close();
			String data = Base64Coder.encodeLines(out.toByteArray());
			
			return data;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}

	public static ItemStack[] itemDeserialize(String data) {

		byte[] byteArray = Base64Coder.decodeLines(data);

		try {
			ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(byteArray));
			ItemStack[] items = new ItemStack[objIn.readInt()];

			for (int i = 0; i < items.length; i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) objIn.readObject();
				items[i] = ItemStack.deserialize(map);
			}

			objIn.close();
			
			return items;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}

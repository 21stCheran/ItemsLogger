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

	public static String itemSerialize(final ItemStack[] invItems) {

		final List<ItemStack> itemlist = Arrays
				.asList(invItems)
				.stream()
				.filter(stack -> stack != null)
				.collect(Collectors.toList());
		
		final ItemStack[] items = itemlist.toArray(new ItemStack[0]);

		try {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeInt(items.length);

			for (final ItemStack item : items) {
				final HashMap<String, Object> map = (HashMap<String, Object>) item.serialize();
				objOut.writeObject(map);
			}

			objOut.close();
			final String data = Base64Coder.encodeLines(out.toByteArray());
			
			return data;
		} catch (final IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}

	public static ItemStack[] itemDeserialize(final String data) {

		final byte[] byteArray = Base64Coder.decodeLines(data);

		try {
			final ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(byteArray));
			final ItemStack[] items = new ItemStack[objIn.readInt()];

			for (int i = 0; i < items.length; i++) {
				final HashMap<String, Object> map = (HashMap<String, Object>) objIn.readObject();
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

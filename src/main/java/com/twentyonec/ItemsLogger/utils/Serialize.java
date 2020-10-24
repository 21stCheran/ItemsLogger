package com.twentyonec.ItemsLogger.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;

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

			//Write the size of the inventory
			objOut.writeInt(items.length);

			//Save every element in the list
			for (ItemStack item : items) {
				HashMap<String, Object> map =  (HashMap<String, Object>) item.serialize(); 

				//Serialize the array
				objOut.writeObject(map);
			}

			objOut.close();
			return new String(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ItemStack[] itemDeserialize(String data) {
		
		Charset charset = Charset.forName("ASCII");
		byte[] byteArray = data.getBytes(charset);
		
		try {
			ObjectInputStream objIn = 
					new ObjectInputStream(new ByteArrayInputStream(byteArray));
			ItemStack[] items = new ItemStack[objIn.readInt()];
			
			//Read the serialized inventory
			for (int i = 0; i < items.length; i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) objIn.readObject();
				items[i] = ItemStack.deserialize(map);
			}
			
			objIn.close();
			return items;
		} catch ( IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}

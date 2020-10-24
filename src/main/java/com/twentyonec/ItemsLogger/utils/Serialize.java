package com.twentyonec.ItemsLogger.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Serialize {

//	HashMap<String, Object> map =  (HashMap<String, Object>) item.serialize(); 
//	plugin.debugMessage(map.toString());
//
//	try {
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		ObjectOutputStream objOut = new ObjectOutputStream(out);
//		objOut.writeObject(map);
//
//		plugin.debugMessage("Stream Serialized: " + out.toString());
//
//		objOut.close();
//
//
//		ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
//		HashMap<String, Object> actual = (HashMap<String, Object>) objIn.readObject();
//		
//		plugin.debugMessage("Stream Deserialized: " + actual.toString());
//		
//	} catch (IOException e) {
//		e.printStackTrace();
//	} catch (ClassNotFoundException e) {
//		e.printStackTrace();
//	}
}

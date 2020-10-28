package com.twentyonec.ItemsLogger.utils;

import com.twentyonec.ItemsLogger.ItemPlayer;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ChatHandler {

	public static TextComponent[] sendLogData(ItemPlayer[] playerlist, String name, String date, String cause, Integer index) {

		if (date == null)
			date = "Most Recent";
		if (cause == null)
			cause = "All";
		if (index == null)
			index = 1;

		TextComponent[] components = new TextComponent[playerlist.length + 1];
		components[0] = new TextComponent("§8[§cItemsLogger§8] \n§6Date §a" 
				+ date + "\n§6Cause §a" + cause
				+ "\n§6Index §a" + index);

		for (int i = 0; i < playerlist.length; i++) {

			TextComponent message = new TextComponent("\n§9Date: §7" 
					+ playerlist[i].date.toString()
					+ " "
					+ playerlist[i].time.toString());
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, 
					"/openitemlog " + name + " view " 
							+ playerlist[i].date.toString() + " " + playerlist[i].time.toString()));
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, 
					new Text( "§cClick to view" ) ) );
			components[i + 1] = message;
		}

		return components;
	}

	public static TextComponent[] sendPlayerData(ItemPlayer itemPlayer, String name) {

		TextComponent[] components = new TextComponent[7];
		components[0] = new TextComponent("§8[§cItemsLogger§8]");
		components[1] = new TextComponent("\n§9Name: §7" + name);
		components[2] = new TextComponent("\n§9Cause: §7" + itemPlayer.cause);
		components[3] = new TextComponent("\n§9Location: §7" + itemPlayer.x + ", " + itemPlayer.y + ", " + itemPlayer.z);
		components[4] = new TextComponent("\n§9Experience: §7" + itemPlayer.experience);
		components[5] = new TextComponent("\n§9Date: §7" + itemPlayer.date.toString());
		components[6] = new TextComponent("\n§9Time §7" + itemPlayer.time.toString());

		for (int i = 1; i < components.length; i++) {

			components[i].setClickEvent(new ClickEvent (ClickEvent.Action.RUN_COMMAND,
					"/plugins"));
			components[i].setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, 
					new Text( "§cClick to open inventory" ) ) );
		}

		return components;
	}

}

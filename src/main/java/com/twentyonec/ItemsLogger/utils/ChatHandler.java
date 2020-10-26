package com.twentyonec.ItemsLogger.utils;

import com.twentyonec.ItemsLogger.ItemPlayer;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ChatHandler {

	public static TextComponent[] sendData(ItemPlayer[] playerlist, String date, String cause) {

		if (date == null)
			date = "Most Recent";
		if (cause == null)
			cause = "All";

		TextComponent[] components = new TextComponent[11];
		components[0] = new TextComponent("§8[§cItemsLogger§8] \n§6Date §7" + date + "\n§6Causes: §7" + cause);

		for (int i = 0; i < playerlist.length; i++) {

			TextComponent message = new TextComponent("\n§9Date: §7 + " 
					+ playerlist[i].date.toString()
					+ " "
					+ playerlist[i].time.toString());
			message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, 
					"/plugins" ) );
			message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, 
					new Text( "§cView Info" ) ) );
			components[i + 1] = message;
		}

		return components;
	}

}

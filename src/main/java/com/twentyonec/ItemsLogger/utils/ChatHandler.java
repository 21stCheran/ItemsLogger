package com.twentyonec.ItemsLogger.utils;

import com.twentyonec.ItemsLogger.ItemPlayer;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ChatHandler {

	public static TextComponent[] sendLogData(final ItemPlayer[] playerlist, final String name, String date, String cause, final int index) {

		if (date == null) {
			date = "Most recent";
		}
		if (cause == null) {
			cause = "All";
		}

		final TextComponent[] components = new TextComponent[playerlist.length + 1];
		final StringBuilder stringBuild = new StringBuilder();
		final StringBuilder messageBuild  = new StringBuilder();
		final StringBuilder commandBuild = new StringBuilder();

		stringBuild.append("§8[§cItemsLogger§8]");
		stringBuild.append("\n§6Date §a" + date);
		stringBuild.append("\n§6Cause §a" + cause);

		components[0] = new TextComponent(stringBuild.toString());
		for (int i = 0; i < playerlist.length; i++) {

			messageBuild.append("\n§9Date: §7");
			messageBuild.append(playerlist[i].getDate());

			final TextComponent message = new TextComponent(stringBuild.toString());

			commandBuild.append("/openitemlog ");
			commandBuild.append(name + " ");
			commandBuild.append("view ");
			commandBuild.append(playerlist[i].getDate());

			message.setClickEvent(new ClickEvent( ClickEvent.Action.RUN_COMMAND, commandBuild.toString()));
			message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text( "§cClick to view")));

			components[i + 1] = message;
			messageBuild.delete(0, messageBuild.length());
			commandBuild.delete(0, commandBuild.length());
		}

		return components;
	}

	public static TextComponent[] sendPlayerData(final ItemPlayer itemPlayer, final String name) {

		final TextComponent[] components = new TextComponent[6];
		components[0] = new TextComponent("§8[§cItemsLogger§8]");
		components[1] = new TextComponent("\n§9Name: §7" + name);
		components[2] = new TextComponent("\n§9Cause: §7" + itemPlayer.getCause());
		components[3] = new TextComponent("\n§9Location: §7" + itemPlayer.getLocation());
		components[4] = new TextComponent("\n§9Experience: §7" + itemPlayer.getExperience());
		components[5] = new TextComponent("\n§9Date: §7" + itemPlayer.getDate());

		for (int i = 1; i < components.length; i++) {

			components[i].setClickEvent(new ClickEvent (ClickEvent.Action.RUN_COMMAND,
					"/openitemlog " + name + " open " + itemPlayer.getDate()));
			components[i].setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, 
					new Text( "§cClick to open inventory" ) ) );
		}

		return components;
	}

}

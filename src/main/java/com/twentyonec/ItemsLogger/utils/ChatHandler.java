package com.twentyonec.ItemsLogger.utils;

import org.bukkit.ChatColor;

import com.twentyonec.ItemsLogger.ItemPlayer;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ChatHandler {
	
	public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&8[&cItemsLogger&8]");
	public static final String NAME = ChatColor.translateAlternateColorCodes('&', "&9Name");
	public static final String DATE = ChatColor.translateAlternateColorCodes('&', "&9Date");
	public static final String CAUSE = ChatColor.translateAlternateColorCodes('&', "&9Cause");
	public static final String LOCATION = ChatColor.translateAlternateColorCodes('&', "&9Location");
	public static final String EXPERIENCE = ChatColor.translateAlternateColorCodes('&', "&9Experience");
	
	public static final String NEW = "\n";
	
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

		stringBuild.append(PREFIX);
		stringBuild.append(NEW + ChatColor.GOLD + "Date " + ChatColor.GREEN + date);
		stringBuild.append(NEW + ChatColor.GOLD +  "Cause " + ChatColor.GREEN + cause);

		components[0] = new TextComponent(stringBuild.toString());
		for (int i = 0; i < playerlist.length; i++) {

			messageBuild.append(NEW + DATE + " " + ChatColor.GRAY);
			messageBuild.append(playerlist[i].getDate());

			final TextComponent message = new TextComponent(messageBuild.toString());

			commandBuild.append("/openitemlog ");
			commandBuild.append(name + " ");
			commandBuild.append("view ");
			commandBuild.append(playerlist[i].getDate());

			message.setClickEvent(new ClickEvent( ClickEvent.Action.RUN_COMMAND, commandBuild.toString()));
			message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text( ChatColor.RED 
																						+ "Click to view")));

			components[i + 1] = message;
			messageBuild.delete(0, messageBuild.length());
			commandBuild.delete(0, commandBuild.length());
		}

		return components;
	}

	public static TextComponent[] sendPlayerData(final ItemPlayer itemPlayer, final String name) {

		final TextComponent[] components = new TextComponent[6];
		components[0] = new TextComponent(PREFIX);
		components[1] = new TextComponent(NEW + NAME + ": " + ChatColor.GRAY + name);
		components[2] = new TextComponent(NEW + CAUSE + ": " + ChatColor.GRAY + itemPlayer.getCause());
		components[3] = new TextComponent(NEW + LOCATION + ": " + ChatColor.GRAY + itemPlayer.getLocation());
		components[4] = new TextComponent(NEW + EXPERIENCE + ": " + ChatColor.GRAY + itemPlayer.getExperience());
		components[5] = new TextComponent(NEW + DATE + ": " + ChatColor.GRAY + itemPlayer.getDate());

		for (int i = 1; i < components.length; i++) {

			components[i].setClickEvent(new ClickEvent (ClickEvent.Action.RUN_COMMAND,
					"/openitemlog " + name + " open " + itemPlayer.getDate()));
			components[i].setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, 
					new Text(ChatColor.RED + "Click to open inventory")));
		}

		return components;
	}

}

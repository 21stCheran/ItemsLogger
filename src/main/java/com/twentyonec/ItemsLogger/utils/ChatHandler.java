package com.twentyonec.ItemsLogger.utils;

import org.bukkit.ChatColor;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.commands.Cmd;

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

		stringBuild.append(Messages.PREFIX.formatMessage());
		stringBuild.append(Messages.NEW.formatMessage() + ChatColor.GOLD + "Date " + ChatColor.GREEN + date);
		stringBuild.append(Messages.NEW.formatMessage() + ChatColor.GOLD +  "Cause " + ChatColor.GREEN + cause);

		components[0] = new TextComponent(stringBuild.toString());
		for (int i = 0; i < playerlist.length; i++) {

			messageBuild.append(Messages.NEW.formatMessage() + Messages.DATE.formatMessage() + " " + ChatColor.GRAY);
			messageBuild.append(playerlist[i].getDate());

			final TextComponent message = new TextComponent(messageBuild.toString());

			commandBuild.append("/" + Cmd.OPENITEMSLOG + " ");
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
		components[0] = new TextComponent(Messages.PREFIX.formatMessage());
		components[1] = new TextComponent(Messages.NEW.formatMessage() + Messages.NAME.formatMessage() + ": " + ChatColor.GRAY + name);
		components[2] = new TextComponent(Messages.NEW.formatMessage() + Messages.CAUSE.formatMessage() + ": " + ChatColor.GRAY + itemPlayer.getCause());
		components[3] = new TextComponent(Messages.NEW.formatMessage() + Messages.LOCATION.formatMessage() + ": " + ChatColor.GRAY + itemPlayer.getLocation());
		components[4] = new TextComponent(Messages.NEW.formatMessage() + Messages.EXPERIENCE.formatMessage() + ": " + ChatColor.GRAY + itemPlayer.getExperience());
		components[5] = new TextComponent(Messages.NEW.formatMessage() + Messages.DATE.formatMessage() + ": " + ChatColor.GRAY + itemPlayer.getDate());

		for (int i = 1; i < components.length; i++) {

			components[i].setClickEvent(new ClickEvent (ClickEvent.Action.RUN_COMMAND,
					"/openitemlog " + name + " open " + itemPlayer.getDate()));
			components[i].setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, 
					new Text(ChatColor.RED + "Click to open inventory")));
		}

		return components;
	}

}

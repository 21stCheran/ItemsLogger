package com.twentyonec.ItemsLogger.commands;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.ChatHandler;
import com.twentyonec.ItemsLogger.utils.Storage;

import net.md_5.bungee.api.chat.TextComponent;

public class Commands implements CommandExecutor {

	ItemsLogger plugin = ItemsLogger.getPlugin();
	Storage storage = Storage.getStorage(plugin);

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

		if (!(sender instanceof Player))
			return false;
		if (args.length < 1)
			return false;

		final Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage("§8[§cItemsLogger§8] §e" + args[0] + " is not online!");
			return false;
		}

		String date = null;
		String cause = null;
		Integer index = null;

		final String dateRegex = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
		final String indexRegex = "^[1-9]{1,3}$";

		final Pattern dateRegexPatten = Pattern.compile(dateRegex);
		final Pattern indexRegexPattern = Pattern.compile(indexRegex);

		for (int i = 1; i < args.length; i++) {

			if (dateRegexPatten.matcher(args[i]).matches()) {
				date = args[i];
			} else if (indexRegexPattern.matcher(args[i]).matches()) {
				index = Integer.valueOf(args[i]);
			} else {
				cause = args[i].replace("_", " ");
			}

		}

		String name = target.getDisplayName();
		final ItemPlayer[] playerlist = storage.retrieveList(target.getUniqueId(), date, cause, index);
		final TextComponent[] components = ChatHandler.sendLogData(playerlist, name, date, cause, index);
		sender.spigot().sendMessage(components);

		return true;
	}

}

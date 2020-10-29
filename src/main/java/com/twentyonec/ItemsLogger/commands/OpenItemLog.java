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

public class OpenItemLog implements CommandExecutor {

	ItemsLogger plugin = ItemsLogger.getPlugin();
	Storage storage = Storage.getStorage(plugin);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player))
			return false;
		if (args.length < 4)
			return false;

		final Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage("§8[§cItemsLogger§8] §e" + args[0] + " is not online!");
			return false;
		}

		String date = null;
		String time = null;
		String type = null;

		final String dateRegex = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
		final String timeRegex = "^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?(?::([0-5]?\\d))?$";

		final Pattern dateRegexPatten = Pattern.compile(dateRegex);
		final Pattern timeRegexPattern = Pattern.compile(timeRegex);

		for (int i = 1; i < args.length; i++) {

			if (dateRegexPatten.matcher(args[i]).matches()) {
				date = args[i];
			} else if (timeRegexPattern.matcher(args[i]).matches()) {
				time = args[i];
			} else {
				type = args[i];
			}

		}

		if ((date == null) || (time == null) || (type == null))
			return false;

		ItemPlayer itemPlayer = storage.retrieveItemPlayer(target.getUniqueId(), date, time);
		if (type.equalsIgnoreCase("view")) {

			TextComponent[] components = ChatHandler.sendPlayerData(itemPlayer, target.getDisplayName());
			sender.spigot().sendMessage(components);

		} else {
			itemPlayer.loadInventory((Player) sender);;
		}
		return true;
	}

}

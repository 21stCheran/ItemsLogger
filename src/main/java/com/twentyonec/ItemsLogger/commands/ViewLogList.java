package com.twentyonec.ItemsLogger.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.ChatHandler;
import com.twentyonec.ItemsLogger.utils.Regex;
import com.twentyonec.ItemsLogger.utils.Storage;

import net.md_5.bungee.api.chat.TextComponent;

public class ViewLogList implements CommandExecutor {

	final private ItemsLogger plugin = ItemsLogger.getPlugin();
	final private Storage storage = Storage.getStorage(plugin);

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§8[§cItemsLogger§8] §eOnly players may execute this command!");
			return false;
		}
		if (args.length < 1) {
			sender.sendMessage("§8[§cItemsLogger§8] §eMust specify player!");
			return false;
		}
		final Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage("§8[§cItemsLogger§8] §e" + args[0] + " is not online!");
			return false;
		}

		String date = null;
		String cause = null;
		int index = 1;

		for (int i = 1; i < args.length; i++) {
			if (Regex.matchDate(args[i])) {
				date = args[i];
			} else if (Regex.matchIndex(args[i])) {
				index = (int) Integer.valueOf(args[i]);
			} else {
				cause = args[i].replace("_", " ");
			}

		}

		final ItemPlayer[] playerlist = storage.retrieveLogList(target.getUniqueId(), date, cause, index);
		final TextComponent[] components = ChatHandler.sendLogData(playerlist, target.getName(), date, cause, index);
		sender.spigot().sendMessage(components);

		return true;
	}

}

package com.twentyonec.ItemsLogger.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.ChatHandler;
import com.twentyonec.ItemsLogger.utils.Permissions;
import com.twentyonec.ItemsLogger.utils.Regex;
import com.twentyonec.ItemsLogger.utils.Storage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class SearchLogs implements CommandInterface {
	
	final private ItemsLogger plugin = ItemsLogger.getPlugin();
	final private Storage storage = Storage.getStorage(plugin);

	@Override
	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender.hasPermission(Permissions.PERMISSION_SEARCH))) {
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW 
								+ " you do not have access to this command.");
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW 
								+ " Only players may execute this command!");
			return false;
		}
		if (args.length < 1) {
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW 
								+ " Must specify player!");
			return false;
		}
		final Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW 
								+ " " + args[0] + " is not online!");
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

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

public class OpenItemLog implements CommandExecutor {

	final private ItemsLogger plugin = ItemsLogger.getPlugin();
	final private Storage storage = Storage.getStorage(plugin);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§8[§cItemsLogger§8] §eOnly players may execute this command!");
			return false;
		}
		if (args.length < 4) {
			sender.sendMessage("§8[§cItemsLogger§8] §eMust specify date, time and type (view or open) !");
			return false;
		}

		final Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage("§8[§cItemsLogger§8] §e" + args[0] + " is not online!");
			return false;
		}

		String date = null;
		String time = null;
		String type = null;

		for (int i = 1; i < args.length; i++) {
			if (Regex.matchDate(args[i])) {
				date = args[i];
			} else if (Regex.matchTime(args[i])) {
				time = args[i];
			} else {
				type = args[i];
			}

		}

		if ((date == null) || (time == null) || (type == null)) {
			sender.sendMessage("§8[§cItemsLogger§8] §eInvalid format for date, time or type!");
			return false;
		}

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

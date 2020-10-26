package com.twentyonec.ItemsLogger.commands;

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
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player))
			return false;
		if (args.length < 1)
			return false;

		Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage("§8[§cItemsLogger§8] §e" + args[0] + " is not online!");
			return false;
		}

		String date = "2020-10-26;
		String cause = null;
		Integer index = null;

		if (args.length == 1) {

			ItemPlayer[] playerlist = storage.retrieveList(target.getUniqueId(), date, cause);
			TextComponent[] components = ChatHandler.sendData(playerlist, date, cause);

			sender.spigot().sendMessage(components);

		}

		return true;
	}

}

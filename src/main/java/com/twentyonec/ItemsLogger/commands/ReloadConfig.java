package com.twentyonec.ItemsLogger.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.ChatHandler;
import com.twentyonec.ItemsLogger.utils.Permissions;

public class ReloadConfig implements CommandInterface{

	ItemsLogger plugin = ItemsLogger.getPlugin();

	@Override
	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission(Permissions.PERMISSION_RELOAD)) {
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW + " Config reloaded.");
			plugin.reload();
			return true;
		}
		sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW + " You do not have access to this command.");
		return false;
	}

}

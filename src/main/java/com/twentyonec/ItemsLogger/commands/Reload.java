package com.twentyonec.ItemsLogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.Permissions;

public class Reload implements CommandInterface{

	ItemsLogger plugin = ItemsLogger.getPlugin();

	@Override
	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission(Permissions.PERMISSION_RELOAD)) {
			sender.sendMessage("§8[§cItemsLogger§8] §eConfig reloaded.");
			plugin.reload();
			return true;
		}
		sender.sendMessage("§8[§cItemsLogger§8] §eyou do not have access to this command.");
		return false;
	}

}

package com.twentyonec.ItemsLogger.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.twentyonec.ItemsLogger.ItemsLogger;

public class Commands implements CommandExecutor {
	
	ItemsLogger plugin = ItemsLogger.getPlugin();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		plugin.debugMessage("command: " + command);
		plugin.debugMessage("label: " + label);
		plugin.debugMessage("args: " + args.toString());
		
		return true;
	}

}

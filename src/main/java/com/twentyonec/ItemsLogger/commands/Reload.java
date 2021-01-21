package com.twentyonec.ItemsLogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.twentyonec.ItemsLogger.ItemsLogger;

public class Reload implements CommandInterface{

	ItemsLogger plugin = ItemsLogger.getPlugin();
	
	@Override
	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("§8[§cItemsLogger§8] §eConfig reloaded.");
		plugin.reload();
		return true;
	}

}

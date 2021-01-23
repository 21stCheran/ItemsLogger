package com.twentyonec.ItemsLogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {

	boolean execute(CommandSender sender, Command command, String label, String[] args);
}

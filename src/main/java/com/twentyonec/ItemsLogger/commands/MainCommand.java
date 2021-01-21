package com.twentyonec.ItemsLogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.twentyonec.ItemsLogger.utils.Permissions;

public class MainCommand implements CommandExecutor{

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

		CommandInterface cmd = null;

		switch (args[0].toLowerCase()) {
		case "reload":
			if (sender.hasPermission(Permissions.PERMISSION_RELOAD)) {
				cmd = new Reload();
			}
			break;
		default:
			if (sender.hasPermission(Permissions.PERMISSION_SEARCH)) {
				cmd = new SearchLogs();
			}
		}
		return cmd.execute(sender, command, label, args);
	}

}

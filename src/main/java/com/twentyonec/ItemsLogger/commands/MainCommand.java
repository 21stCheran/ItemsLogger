package com.twentyonec.ItemsLogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor{

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

		CommandInterface cmd = null;

		switch (args[0].toLowerCase()) {
		case Cmd.RELOAD:
			cmd = new Reload();
			break;
		default:
			cmd = new SearchLogs();
		}
		return cmd.execute(sender, command, label, args);
	}

}

class Cmd {
	public final static String RELOAD = "reload";
}
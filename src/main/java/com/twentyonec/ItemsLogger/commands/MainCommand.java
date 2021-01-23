package com.twentyonec.ItemsLogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.twentyonec.ItemsLogger.utils.Messages;

public class MainCommand implements CommandExecutor{

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

		if (args.length == 0 || args == null) {
			StringBuilder sb = new StringBuilder(Messages.PREFIX.formatMessage());
			sb.append(Messages.NEW.formatMessage() + Messages.HELP_SEARCH.formatMessage());
			sb.append(Messages.NEW.formatMessage() + Messages.HELP_OPEN.formatMessage());
			sb.append(Messages.NEW.formatMessage() + Messages.HELP_RELOAD.formatMessage());
			sender.sendMessage(sb.toString());
			
			return true;
		}
		
		CommandInterface cmd;
		switch (args[0].toLowerCase()) {
		case Cmd.RELOAD:
			cmd = new ReloadConfig();
			break;
		default:
			cmd = new SearchLogs();
		}
		return cmd.execute(sender, command, label, args);
	}

}

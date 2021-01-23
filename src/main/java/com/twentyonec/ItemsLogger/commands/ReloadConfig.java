package com.twentyonec.ItemsLogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.Messages;
import com.twentyonec.ItemsLogger.utils.Permissions;

public class ReloadConfig implements CommandInterface{

	ItemsLogger plugin = ItemsLogger.getPlugin();

	@Override
	public boolean execute(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission(Permissions.PERMISSION_RELOAD)) {
			sender.sendMessage(Messages.PREFIX.formatMessage() + Messages.CONFIG_RELOADED.formatMessage());
			plugin.reload();
			return true;
		}
		sender.sendMessage(Messages.PREFIX.formatMessage() + Messages.NO_PERMISSION.formatMessage(Permissions.PERMISSION_RELOAD));
		return false;
	}

}

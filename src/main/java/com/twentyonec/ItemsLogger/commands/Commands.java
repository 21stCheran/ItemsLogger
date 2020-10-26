package com.twentyonec.ItemsLogger.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.Storage;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

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

		CommandsUtil commandsUtil;

		if (args.length == 1) {
			commandsUtil = new CommandsUtil(target.getUniqueId());
			Arrays.asList(commandsUtil.retrieveList()).forEach(v -> {

				TextComponent message = new TextComponent(v.toString());
				message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, 
						new Text("21C is cool")));
				message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, 
						"/plugins" ) );

				
				sender.spigot().sendMessage(message);
			});;


		}

		return true;
	}

}

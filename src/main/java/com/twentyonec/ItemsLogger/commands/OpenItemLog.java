package com.twentyonec.ItemsLogger.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.ChatHandler;
import com.twentyonec.ItemsLogger.utils.Messages;
import com.twentyonec.ItemsLogger.utils.Regex;
import com.twentyonec.ItemsLogger.utils.Serialize;
import com.twentyonec.ItemsLogger.utils.Storage;

import net.md_5.bungee.api.chat.TextComponent;

public class OpenItemLog implements CommandExecutor {

	final private ItemsLogger plugin = ItemsLogger.getPlugin();
	final private Storage storage = Storage.getStorage(plugin);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.PREFIX.formatMessage() + Messages.NON_PLAYER.formatMessage());
			return false;
		}
		if (args.length < 4) {
			sender.sendMessage(Messages.PREFIX.formatMessage() + Messages.UNSPECIFIED_ARGS.formatMessage());
			return false;
		}

		final Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage(Messages.PREFIX.formatMessage() + Messages.OFFLINE_PLAYER.formatMessage(args[0]));
			return false;
		}

		String date = null;
		String time = null;
		String type = null;

		for (int i = 1; i < args.length; i++) {
			if (Regex.matchDate(args[i])) {
				date = args[i];
			} else if (Regex.matchTime(args[i])) {
				time = args[i];
			} else {
				type = args[i];
			}

		}

		if ((date == null) || (time == null) || (type == null)) {
			sender.sendMessage(Messages.PREFIX.formatMessage() + Messages.INVALID_ARGS.formatMessage());
			return false;
		}

		ItemPlayer itemPlayer = storage.retrieveItemPlayer(target.getUniqueId(), date, time);
		if (itemPlayer == null) {
			sender.sendMessage(Messages.PREFIX.formatMessage() + Messages.INVALID_ARGS.formatMessage());
			return false;
		}
		if (type.equalsIgnoreCase(Cmd.VIEW)) {

			TextComponent[] components = ChatHandler.sendPlayerData(itemPlayer, target.getName());
			sender.spigot().sendMessage(components);

		} else if (type.equalsIgnoreCase(Cmd.OPEN)){

			ItemStack[] items;
			try {
				items = Serialize.itemStackArrayFromBase64(itemPlayer.getInventory());
				String inventoryName = itemPlayer.getCause() + " Inventory";
				final Inventory inv = Bukkit.createInventory(null, 54, inventoryName);
				inv.addItem(items);
				((HumanEntity) sender).openInventory(inv);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

}

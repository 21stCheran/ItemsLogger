package com.twentyonec.ItemsLogger.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW 
								+ " Only players may execute this command!");
			return false;
		}
		if (args.length < 4) {
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW 
								+ " Must specify date, time and type (view or open)!");
			return false;
		}

		final Player target = (Bukkit.getServer().getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW + " " + args[0] + " is not online!");
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
			sender.sendMessage(ChatHandler.PREFIX + ChatColor.YELLOW 
								+ " Invalid format for date, time or type!");
			return false;
		}

		ItemPlayer itemPlayer = storage.retrieveItemPlayer(target.getUniqueId(), date, time);
		if (type.equalsIgnoreCase("view")) {

			TextComponent[] components = ChatHandler.sendPlayerData(itemPlayer, target.getName());
			sender.spigot().sendMessage(components);

		} else {
			
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

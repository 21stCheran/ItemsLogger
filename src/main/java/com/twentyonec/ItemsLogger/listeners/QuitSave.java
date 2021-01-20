package com.twentyonec.ItemsLogger.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;

public class QuitSave implements Listener {
	final private ItemsLogger plugin = ItemsLogger.getPlugin();

	@EventHandler
	public void onDeathSave(final PlayerQuitEvent event) {

		final Player player = event.getPlayer();
		if (player.hasPermission("itemslogger.log")) {
			final ItemPlayer itemPlayer = new ItemPlayer(player, "Quit");

			new BukkitRunnable() {
				@Override
				public void run() {
					itemPlayer.savePlayer();
				}
			}.runTaskAsynchronously(plugin);
		}
	}
}

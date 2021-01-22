package com.twentyonec.ItemsLogger.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;

public class DeathSave implements Listener {

	final private ItemsLogger plugin = ItemsLogger.getPlugin();

	@EventHandler
	public void onDeathSave(final PlayerDeathEvent event) {

		final Player player = event.getEntity();
		if (player.hasPermission("itemslogger.log")) {
			final String cause = event.getDeathMessage();
			final ItemPlayer itemPlayer = new ItemPlayer(player, cause);

			plugin.debugMessage("Attempting to save player data");
			new BukkitRunnable() {
				@Override
				public void run() {
					itemPlayer.savePlayer();
				}
			}.runTaskAsynchronously(plugin);
		}
	}
}

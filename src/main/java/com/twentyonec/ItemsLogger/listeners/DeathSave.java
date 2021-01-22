package com.twentyonec.ItemsLogger.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;
import com.twentyonec.ItemsLogger.utils.Cause;
import com.twentyonec.ItemsLogger.utils.Permissions;

public class DeathSave implements Listener {

	final private ItemsLogger plugin = ItemsLogger.getPlugin();

	@EventHandler
	public void onDeathSave(final PlayerDeathEvent event) {

		final Player player = event.getEntity();
		if ((plugin.getConfigManager().getDeath()) && 
				(player.hasPermission(Permissions.PERMISSION_LOG))) {
			
			final ItemPlayer itemPlayer = new ItemPlayer(player, Cause.DEATH);
			new BukkitRunnable() {
				@Override
				public void run() {
					itemPlayer.savePlayer();
				}
			}.runTaskAsynchronously(plugin);
		}
	}
}

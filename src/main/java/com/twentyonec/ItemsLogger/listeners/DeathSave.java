package com.twentyonec.ItemsLogger.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.twentyonec.ItemsLogger.ItemPlayer;
import com.twentyonec.ItemsLogger.ItemsLogger;

public class DeathSave implements Listener {

	ItemsLogger plugin = ItemsLogger.getPlugin();

	@EventHandler
	public void onDeathSave(PlayerDeathEvent event) {

		Player player = event.getEntity();
		String cause = event.getDeathMessage();	
		ItemPlayer itemPlayer = new ItemPlayer(player, cause);

		plugin.debugMessage("Attempting to save player data");
		itemPlayer.savePlayer();
	}

}
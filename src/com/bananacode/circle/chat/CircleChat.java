package com.bananacode.circle.chat;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.bananacode.circle.classes.Classes;

public class CircleChat extends PlayerListener {
	public static final int chatDistance = 30;
	JavaPlugin jp;
	PluginManager pm;

	CircleChat(JavaPlugin jp, PluginManager pm) {
		this.jp = jp;
		this.pm = pm;
	}

	public void register() {
		pm.registerEvent(Event.Type.PLAYER_CHAT, this, Event.Priority.Low, jp);
	}

	public void onPlayerChat(PlayerChatEvent event) {
		String message = event.getFormat();
		Classes playerClasses = new Classes();
		Player player = event.getPlayer();
		String playerClass = playerClasses.getClass(player);
		Player[] players = jp.getServer().getOnlinePlayers();
		for (Player recipient : players) {
			if (playerClasses.isInClass(recipient, playerClass)) {
				sendMessage(recipient, message);
			} else if (player.getWorld() == recipient.getWorld()) {
				Location loc1 = player.getLocation();
				Location loc2 = recipient.getLocation();
				int distance = (int) loc1.distance(loc2);
				if (distance < chatDistance)
					sendMessage(recipient, message);
			}
		}
	}

	public void sendMessage(Player player, String message) {
		player.sendMessage(message);
	}
}
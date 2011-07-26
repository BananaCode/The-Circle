package com.bananacode.circle.classes;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Mana {
	public static final int manaLimit = 1000;
	public static HashMap<String, Integer> playerMana = null;

	public Mana() {
		if (Mana.playerMana == null)
			Mana.playerMana = new HashMap<String, Integer>();
	}

	public boolean manaCheck(Player player, int amount) {
		if (player.isOp())
			return true;
		String playerName = player.getName();
		if (!playerMana.containsKey(playerName))
			playerMana.put(playerName, manaLimit);
		/*
		 * Remove "amount" mana from the player
		 */
		int oldMana = playerMana.get(playerName);
		int newMana = oldMana - amount;
		if (newMana <= 0)
			return false;
		playerMana.remove(playerName);
		playerMana.put(playerName, newMana);
		return true;
	}

	public int manaCount(Player player) {
		String playerName = player.getName();

		if (!playerMana.containsKey(playerName))
			playerMana.put(playerName, manaLimit);

		int oldMana = playerMana.get(playerName);
		return oldMana;
	}

	public void clear() {
		Mana.playerMana = null;
	}
}

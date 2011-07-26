package com.bananacode.circle.classes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class Classes {
	public static HashMap<String, String> playerClasses = null;
	public static final String defaultName = "Undecided";
	public static final File configFile = new File("circle.yml");
	public Configuration config;
	
	public Classes() {
	
		if (Classes.playerClasses == null)
			Classes.playerClasses = new HashMap<String, String>();
	
	if(!configFile.exists())
	{
		try {
			configFile.createNewFile();
		} catch (IOException e) {
			System.err.println("Error creating configFile");
		}
	}
	this.config = new Configuration(configFile);
	}
	
	public void loadPlayer(Player player)
	{
	config.load();
	String playerClass = config.getString(player.getName(),defaultName);
	setClass(player, playerClass);
	}
	
	public boolean isInClass(Player player, String playerClass) {
		if (player.isOp())
			return true;

		if (playerClasses.containsKey(player.getName())) {
			if (playerClasses.get(player.getName()).equalsIgnoreCase(
					playerClass)) {
				return true;
			}
		}
		return false;
	}

	public void setClass(Player player, String playerClass) {
		if (playerClasses.containsKey(player.getName()))
			playerClasses.remove(player.getName());
		playerClasses.put(player.getName(), playerClass);
		config.setProperty(player.getName(), playerClass);
		config.save();
	}

	public String getClass(Player player) {
		loadPlayer(player);
		if (playerClasses.containsKey(player.getName()))
			return playerClasses.get(player.getName());
		return defaultName;
	}

}
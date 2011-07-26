package com.bananacode.circle.defaultpowers;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Power {
	Power(JavaPlugin jp, PluginManager pm)
	{
		new Spirit(jp, pm);
		new Earth(jp, pm);
		new Fire(jp, pm);
		new Water(jp, pm);
		new Wind(pm, jp);
	}

}

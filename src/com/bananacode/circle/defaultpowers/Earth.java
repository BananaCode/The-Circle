package com.bananacode.circle.defaultpowers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.bananacode.circle.classes.Classes;
import com.bananacode.circle.classes.Mana;

public class Earth {
	Earth(JavaPlugin jp, PluginManager pm) {
		new EarthListener(jp, pm).register();
		new EarthListener2(jp, pm).register();
	}
}

class EarthListener extends BlockListener {

	PluginManager pm;
	JavaPlugin jp;

	EarthListener(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, this, Event.Priority.Low, jp);
	}

	public void onBlockDamage(BlockDamageEvent event) {
		if (event.getBlock().getType() != Material.BEDROCK) {
			Player player = event.getPlayer();
			if (new Classes().isInClass(player, "Earth")) {
				if (new Mana().manaCheck(player, 1)) {
					Location loc = event.getBlock().getLocation();
					World world = loc.getWorld();
					Material mat = event.getBlock().getType();
					byte data = event.getBlock().getData();
					short zero = (short) 0;
					ItemStack item = new ItemStack(mat, 5, zero, data);
					event.getBlock().setType(Material.AIR);
					world.dropItemNaturally(loc, item);
					event.setCancelled(true);
				}
			}
		}
	}
}

class EarthListener2 extends PlayerListener {
	PluginManager pm;
	JavaPlugin jp;

	EarthListener2(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this, Event.Priority.Low,
				jp);
	}

	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = (Player) event.getPlayer();
			if (new Classes().isInClass(player, "Earth")) {
				if (event.getClickedBlock().getType() == Material.GRASS) {
					if (new Mana().manaCheck(player, 50)) {
						Block b = event.getClickedBlock().getRelative(0, 1, 0);
						b.setType(Material.SAPLING);
						byte data = (byte) (Math.random() * 3 - 1);
						b.setData(data);
					}
				}
			}
		}
	}
}
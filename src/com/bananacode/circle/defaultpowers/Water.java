package com.bananacode.circle.defaultpowers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.bananacode.circle.classes.Classes;
import com.bananacode.circle.classes.Mana;

public class Water {

	Water(JavaPlugin jp, PluginManager pm) {

		new WaterListener(jp, pm).register();
		new WaterListener2(jp, pm).register();
	}

}

class WaterListener extends EntityListener {
	PluginManager pm;
	JavaPlugin jp;

	WaterListener(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Event.Priority.Low, jp);
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (new Classes().isInClass(player, "Water")
					&& event.getCause() == DamageCause.DROWNING)
				event.setCancelled(true);
		}
	}
}

class WaterListener2 extends PlayerListener {
	PluginManager pm;
	JavaPlugin jp;

	WaterListener2(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this, Event.Priority.Low,
				jp);
	}

	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK
				|| event.getAction() == Action.RIGHT_CLICK_AIR) {
			Player player = (Player) event.getPlayer();
			if (new Classes().isInClass(player, "Water")) {
				if (player.getEyeLocation().getBlock().isLiquid() && player.getItemInHand().getType() == Material.WOOD_SWORD) {
					if (new Mana().manaCheck(player, 5)) {
						Vector v = player.getVelocity();
						v.multiply(3);
						player.setVelocity(v);
					}
				}
			}
		}
	}
}
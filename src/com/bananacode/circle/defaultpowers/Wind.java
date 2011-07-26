package com.bananacode.circle.defaultpowers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.bananacode.circle.classes.Classes;
import com.bananacode.circle.classes.Mana;

public class Wind {
	Wind(PluginManager pm, JavaPlugin jp) {
		new WindListener(jp, pm).register();
		new WindListener2(jp, pm).register();
	}

}

class WindListener extends PlayerListener {
	PluginManager pm;
	JavaPlugin jp;

	WindListener(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.PLAYER_INTERACT, this, Event.Priority.Low,
				jp);
	}

	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = (Player) event.getPlayer();
			if (new Classes().isInClass(player, "Wind")) {
				if (player.getItemInHand().getType() == Material.FEATHER) {
					if (new Mana().manaCheck(player, 3)) {
						Vector v = player.getVelocity();
						v.setY(1);
						player.setVelocity(v);
					}
				}
			}
		}
	}
}

class WindListener2 extends EntityListener {
	PluginManager pm;
	JavaPlugin jp;

	WindListener2(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Event.Priority.Low, jp);
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (new Classes().isInClass(player, "Wind")
					&& (event.getCause() == DamageCause.FALL || event
							.getCause() == DamageCause.VOID)) {
				event.setCancelled(true);
			}

		}
	}
}
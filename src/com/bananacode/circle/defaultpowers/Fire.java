package com.bananacode.circle.defaultpowers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.bananacode.circle.classes.Classes;
import com.bananacode.circle.classes.Mana;

public class Fire {
	Fire(JavaPlugin jp, PluginManager pm) {

		new FireListener(jp, pm).register();
		new FireListener2(jp, pm).register();
	}
}

class FireListener extends EntityListener {
	PluginManager pm;
	JavaPlugin jp;

	FireListener(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Event.Priority.Low, jp);
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (new Classes().isInClass(player, "Fire")
					&& (event.getCause() == DamageCause.FIRE || event
							.getCause() == DamageCause.FIRE_TICK)) {
				event.setCancelled(true);
			}

		}
	}
}

class FireListener2 extends PlayerListener {
	PluginManager pm;
	JavaPlugin jp;

	FireListener2(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, this,
				Event.Priority.Low, jp);
	}

	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) event.getRightClicked();
			Player player = event.getPlayer();
			if (new Classes().isInClass(player, "Fire")) {
				if (new Mana().manaCheck(player, 10))
					target.setFireTicks(1000);
			}
		}
	}
}
package com.bananacode.circle.defaultpowers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.bananacode.circle.classes.Classes;
import com.bananacode.circle.classes.Mana;

public class Spirit {
	Spirit(JavaPlugin jp, PluginManager pm) {
		new SpiritListener(jp, pm).register();
		new SpiritListener2(jp, pm).register();
	}
}

class SpiritListener extends EntityListener {
	PluginManager pm;
	JavaPlugin jp;

	SpiritListener(JavaPlugin jp, PluginManager pm) {
		this.pm = pm;
		this.jp = jp;
	}

	public void register() {
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, this, Event.Priority.Low, jp);
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (new Classes().isInClass(player, "Spirit")) {
				if (new Mana().manaCheck(player, 20))
					event.setCancelled(true);
			}

		}
	}
}

class SpiritListener2 extends PlayerListener {
	PluginManager pm;
	JavaPlugin jp;

	SpiritListener2(JavaPlugin jp, PluginManager pm) {
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
			if (new Classes().isInClass(player, "Spirit")) {
				if (new Mana().manaCheck(player, 30))
				{
					Vector v= target.getVelocity();
					v.setY(1);
					v.multiply(5);
				target.setVelocity(v);
				}
			}
		}
	}
}
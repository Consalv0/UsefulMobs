package mx.consalvo.UsefulMobs.listeners;

import mx.consalvo.UsefulMobs.UsefulMobs;
import mx.consalvo.UsefulMobs.EggType;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class UsefulMobsCatchEntityListener implements Listener {
	FileConfiguration config;
	JavaPlugin plugin;
	private final Boolean usePermissions;
	private final String NoPermissions;

	public UsefulMobsCatchEntityListener(JavaPlugin plugin) {
		this.config = plugin.getConfig();
		this.plugin = plugin;
		this.usePermissions = this.config.getBoolean("usePermissions", true);
		this.NoPermissions = this.config.getString("Messages.NoPermissions");
	}

	@EventHandler
	public void onEntityHitByEgg(EntityDamageEvent event) {
		EntityDamageByEntityEvent damageEvent = null;
		Egg egg = null;
		EggType eggType = null;
		double vaultCost = 0.0;
		Entity entity = event.getEntity();

		if (!(event instanceof EntityDamageByEntityEvent)) {
			return;
		}

		damageEvent = (EntityDamageByEntityEvent) event;

		if (!(damageEvent.getDamager() instanceof Egg)) {
			return;
		}

		egg = (Egg) damageEvent.getDamager();
		eggType = EggType.getEggType(entity);

		if (eggType == null) {
			return;
		}
			UsefulMobs.eggs.add(egg);
			if (entity instanceof Animals) {
				if (!((Animals) entity).isAdult()) {
					return;
				}
			}
			if (entity instanceof Tameable) {
				Player player = (Player) egg.getShooter();
				if (((Tameable) entity).getOwner() == (player)) {
				} else {
					if (((Tameable) entity).isTamed()){
					return;
					}
				}
			}

		if (egg.getShooter() instanceof Player) {
			Player player = (Player) egg.getShooter();

			if (this.usePermissions) {
				if (!player.hasPermission("usefulmobs.catch."+ eggType.getFriendlyName().toLowerCase())) {
					player.sendMessage(ChatColor.RED  + NoPermissions);
						player.getInventory().addItem(new ItemStack(344, 1));
					return;
				}
			}
				vaultCost = config.getDouble("VaultCost." + eggType.getFriendlyName());
				if (!UsefulMobs.economy.has(player.getName(), vaultCost)) {
					player.sendMessage(String.format(config.getString("Messages.MissingMoney"), vaultCost));
						player.getInventory().addItem(new ItemStack(344, 1));
					return;
				} else {
					UsefulMobs.economy.withdrawPlayer(player.getName(), vaultCost + (Math.random()));
					player.sendMessage(String.format(config.getString("Messages.BoughtEgg"), vaultCost));
			}
			entity.remove();
			entity.getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 0);
			entity.getWorld().playEffect(entity.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 9);
			entity.getWorld().dropItem(entity.getLocation(),new ItemStack(383, 1, eggType.getCreatureId()));
			
			if (!UsefulMobs.eggs.contains(egg)) {
				UsefulMobs.eggs.add(egg);
			}
		}
	}
}
package mx.consalvo.UsefulMobs.listeners;

import mx.consalvo.UsefulMobs.UsefulMobs;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class UsefulMobsPlayerListener implements Listener {
	private final float FireballExplosion;
	private final String NoPermissions;
	
	FileConfiguration config;
	JavaPlugin plugin;
	
	public UsefulMobsPlayerListener(JavaPlugin plugin) {
		this.config = plugin.getConfig();
		this.plugin = plugin;
		this.FireballExplosion = this.config.getInt("FireballExplosion", 0);
		this.NoPermissions = this.config.getString("Messages.NoPermissions");
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = (Player)event.getPlayer();
		if(player.getItemInHand().getType() == Material.FIREBALL){
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR)){
				if(!player.hasPermission("usefulmobs.fireball")){
					player.sendMessage(ChatColor.RED  + NoPermissions);
				}else{
				if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
					player.getInventory().addItem(new ItemStack (385, 1));
					return;}
					Fireball projectile = player.getWorld().spawn(event.getPlayer().getLocation()
							.add(new Vector(0.0D, 1.0D, 0.0D)), Fireball.class);
					projectile.setShooter(player);
					projectile.setYield((float) FireballExplosion);
					if(!(player.getGameMode() == GameMode.CREATIVE)){
					player.getInventory().removeItem(new ItemStack (385, 1));}
					player.getWorld().playEffect(player.getLocation(), Effect.BOW_FIRE, 1);
				}
			}
	    }
	}
	@EventHandler
	public void onPlayerEggThrow(PlayerEggThrowEvent event) {
		if (UsefulMobs.eggs.contains(event.getEgg())) {
			event.setHatching(false);
			UsefulMobs.eggs.remove(event.getEgg());
		}
	}
}
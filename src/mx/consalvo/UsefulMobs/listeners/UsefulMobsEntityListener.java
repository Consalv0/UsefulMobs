
package mx.consalvo.UsefulMobs.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class UsefulMobsEntityListener implements Listener {
	private final String MissingString;
	private final String MissingSulphur;
	private final String CookedFish;
	private final String MissingEyeEnder;
	private final Boolean LightExplosion;

	FileConfiguration config;
	JavaPlugin plugin;

	public UsefulMobsEntityListener(JavaPlugin plugin) {
		this.config = plugin.getConfig();
		this.plugin = plugin;
		this.LightExplosion = this.config.getBoolean("LightExplosion", true);
		this.MissingString = this.config.getString("Messages.MissingString");
		this.CookedFish = this.config.getString("Messages.CookedFish");
		this.MissingSulphur = this.config.getString("Messages.MissingSulphur");
		this.MissingEyeEnder = this.config.getString("Messages.MissingEyeEnder");
	}

	@EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof LivingEntity) {
			LivingEntity clickedEntity = (LivingEntity)event.getRightClicked();
			Player player = (Player)event.getPlayer();
		
		if (clickedEntity instanceof Spider) {
			if (player.getItemInHand().getType() == Material.STRING) {
				for(int i = 0; i < 4; i++){
				if (!player.getInventory().contains(Material.STRING, i)){
				player.sendMessage(ChatColor.RED  + MissingString);
					return;}
				}
			((Spider) clickedEntity).setTarget(null);
			player.getInventory().removeItem(new ItemStack (287, 3));
			clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(30, 1));
			clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.STEP_SOUND, 30);
				}
			}
		
		if (clickedEntity instanceof Squid) {
			if (player.getItemInHand().getType() == Material.COOKED_FISH) {
				player.sendMessage(ChatColor.RED  + CookedFish);
					return;
			}
			if (player.getItemInHand().getType() == Material.RAW_FISH) {
			((Squid) clickedEntity).setTarget(null);
			player.getInventory().removeItem(new ItemStack (349, 1));
			clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(19, 1));
			clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.STEP_SOUND, 19);
			clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.ENDER_SIGNAL, 1);
				}
			}
		
		if (clickedEntity instanceof Slime) {
			if (player.getItemInHand().getType() == Material.SHEARS) {
				if (((Slime) clickedEntity).getSize() == 4){
					if (clickedEntity.isDead()){
						return;}
					clickedEntity.damage(4);
					clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(341, 2));
					clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.STEP_SOUND, 35,5);
					return;}
				if (((Slime) clickedEntity).getSize() == 3){
					if (clickedEntity.isDead()){
						return;}
					clickedEntity.damage(4);
					clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(341, 1));
					clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.STEP_SOUND, 35,5);
					return;}
				if (((Slime) clickedEntity).getSize() == 2){
					if (clickedEntity.isDead()){
						return;}
					clickedEntity.damage(4);
					clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(341, 1));
					clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.STEP_SOUND, 35,5);
					return;}
				if (((Slime) clickedEntity).getSize() == 1){
					if (clickedEntity.isDead()){
						return;}
					clickedEntity.damage(4);
					clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.STEP_SOUND, 35,5);
					return;}
				}
			}
		
		if (clickedEntity instanceof Blaze) {
			if (player.getItemInHand().getType() == Material.SULPHUR) {
				if (!player.getInventory().contains(Material.SULPHUR)) {
					player.sendMessage(ChatColor.RED  + MissingSulphur);
					return;
				}
			((Blaze) clickedEntity).setTarget(null);
			player.getInventory().removeItem(new ItemStack (289, 1));
			clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(385, 1));
			clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.EXTINGUISH, 2);
				}
			}
		
		if (clickedEntity instanceof Enderman) {
			if (player.getItemInHand().getType() == Material.EYE_OF_ENDER) {
				for(int i = 0; i < 21; i++){
				if (!player.getInventory().contains(Material.EYE_OF_ENDER, i)){
					player.sendMessage(ChatColor.RED  + MissingEyeEnder);
					return;}
				}
			((Enderman) clickedEntity).setTarget(null);
			player.getInventory().removeItem(new ItemStack (381, 20));
			clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(122, 1));
			clickedEntity.getWorld().playEffect(clickedEntity.getEyeLocation(), Effect.ENDER_SIGNAL, 2);
			clickedEntity.getWorld().playEffect(clickedEntity.getLocation(), Effect.STEP_SOUND, 122);
				}
			}
		}
	}
	@EventHandler
    public void onEntityExplode(ExplosionPrimeEvent event) throws InterruptedException {
		if (this.LightExplosion){
		Entity entity = event.getEntity();
		Block block = event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation());
		if (entity instanceof Creeper){
			if (!(block.getLightLevel() < 8)){
				event.setCancelled(true);
				((Creeper) entity).damage(block.getLightLevel());
				((Creeper) entity).getWorld().playEffect(entity.getLocation(), Effect.EXTINGUISH, 1);
				((Creeper) entity).getWorld().playEffect(((Creeper) entity).getEyeLocation(), Effect.SMOKE, 1000);
				((Creeper) entity).getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 0);
				((Creeper) entity).setFireTicks(1000);
				}
			}
		}
	}
}
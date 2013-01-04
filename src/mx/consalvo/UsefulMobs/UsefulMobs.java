package mx.consalvo.UsefulMobs;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import mx.consalvo.UsefulMobs.listeners.UsefulMobsCatchEntityListener;
import mx.consalvo.UsefulMobs.listeners.UsefulMobsEntityListener;
import mx.consalvo.UsefulMobs.listeners.UsefulMobsPlayerListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class UsefulMobs extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");

	public static List<Egg> eggs = new ArrayList<Egg>();
	public static Economy economy = null;

	public void onDisable() {
		log.info(this.getDescription().getName() + " v"
				+ this.getDescription().getVersion() + " is disabled!");
	}

	public void onEnable() {
		this.CheckConfigurationFile();
		PluginManager pm = this.getServer().getPluginManager();
		log.info(this.getDescription().getName() + " v"
				+ this.getDescription().getVersion() + " is enabled!");

		final UsefulMobsPlayerListener playerListener = new UsefulMobsPlayerListener(this);
		final UsefulMobsEntityListener entityListener = new UsefulMobsEntityListener(this);
		final UsefulMobsCatchEntityListener catchEntityListener = new UsefulMobsCatchEntityListener(this);

		pm.registerEvents(playerListener, this);
		pm.registerEvents(entityListener, this);		
		pm.registerEvents(catchEntityListener, this);

		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Economy> economyProvider = getServer()
					.getServicesManager().getRegistration(Economy.class);
			if (economyProvider != null) {
				economy = economyProvider.getProvider();
			}
		}
	}

	public void CheckConfigurationFile() {
		double configVersion = this.getConfig().getDouble("ConfigVersion", 0.0);
		if (configVersion == 2.3) {
			//
			this.saveConfig();
		} else if (configVersion != 2.3) {
//			if(this.getConfig().getString("Language") == "es" ){
			this.getConfig().set("ConfigVersion", 2.3);
			this.getConfig().set("Language", "es");
			this.getConfig().set("usePermisos", "true");
			this.getConfig().set("LightExplosion", "true");
			this.getConfig().set("FireballExplosion", 1);
			this.getConfig().set(("VaultCost.Pig"), 10);
			this.getConfig().set(("VaultCost.Sheep"), 10);
			this.getConfig().set(("VaultCost.MushroomCow"), 100);
			this.getConfig().set(("VaultCost.Cow"), 20);
			this.getConfig().set(("VaultCost.Chicken"), 5);
			this.getConfig().set(("VaultCost.Squid"), 6);
			this.getConfig().set(("VaultCost.Wolf"), 10);
			this.getConfig().set(("VaultCost.Creeper"), 50);
			this.getConfig().set(("VaultCost.Skeleton"), 50);
			this.getConfig().set(("VaultCost.CaveSpider"), 10);
			this.getConfig().set(("VaultCost.Spider"), 15);
			this.getConfig().set(("VaultCost.PigZombie"), 50);
			this.getConfig().set(("VaultCost.Zombie"), 20);
			this.getConfig().set(("VaultCost.MagmaCube"), 30);
			this.getConfig().set(("VaultCost.Slime"), 20);
			this.getConfig().set(("VaultCost.Ghast"), 100);
			this.getConfig().set(("VaultCost.Enderman"), 1000);
			this.getConfig().set(("VaultCost.Silverfish"), 3);
			this.getConfig().set(("VaultCost.Blaze"), 50);
			this.getConfig().set(("VaultCost.Villager"), 30);
			this.getConfig().set(("VaultCost.Ocelot"), 10);
			this.getConfig().set("Messages.NoPermissions", "No tienes permisos de hacer eso.");
			this.getConfig().set("Messages.MissingString", "No tienes suficientes hilos.");
			this.getConfig().set("Messages.CookedFish", "Debe ser pescado crudo.");
			this.getConfig().set("Messages.MissingSulphur", "No tienes suficiente polvora.");
			this.getConfig().set("Messages.MissingEyeEnder", "Este enderman no confia en ti aun.");
			this.getConfig().set("Messages.MissingMoney", "§cNo tienenes suficiente dinero para capturarlo!");
			this.getConfig().set("Messages.BoughtEgg", "§eCapturado por §d%s §cPesos§e!");
			this.saveConfig();
			
/*        }else if (this.getConfig().getString("Language") == "en" ){
			this.getConfig().set("ConfigVersion", 2.3);
			this.getConfig().set("Language", "en");
			this.getConfig().set("usePermisos", "true");
			this.getConfig().set("LightExplosion", "true");
			this.getConfig().set("FireballExplosion", 1);
			this.getConfig().set(("VaultCost.Pig"), 10);
			this.getConfig().set(("VaultCost.Sheep"), 10);
			this.getConfig().set(("VaultCost.MushroomCow"), 100);
			this.getConfig().set(("VaultCost.Cow"), 20);
			this.getConfig().set(("VaultCost.Chicken"), 5);
			this.getConfig().set(("VaultCost.Squid"), 6);
			this.getConfig().set(("VaultCost.Wolf"), 10);
			this.getConfig().set(("VaultCost.Creeper"), 50);
			this.getConfig().set(("VaultCost.Skeleton"), 50);
			this.getConfig().set(("VaultCost.CaveSpider"), 10);
			this.getConfig().set(("VaultCost.Spider"), 15);
			this.getConfig().set(("VaultCost.PigZombie"), 50);
			this.getConfig().set(("VaultCost.Zombie"), 20);
			this.getConfig().set(("VaultCost.MagmaCube"), 30);
			this.getConfig().set(("VaultCost.Slime"), 20);
			this.getConfig().set(("VaultCost.Ghast"), 100);
			this.getConfig().set(("VaultCost.Enderman"), 1000);
			this.getConfig().set(("VaultCost.Silverfish"), 3);
			this.getConfig().set(("VaultCost.Blaze"), 50);
			this.getConfig().set(("VaultCost.Villager"), 30);
			this.getConfig().set(("VaultCost.Ocelot"), 10);
			this.getConfig().set("Messages.NoPermissions", "You don't have permissions to do that!");
			this.getConfig().set("Messages.MissingString", "You don't have enough strings.");
			this.getConfig().set("Messages.CookedFish", "It must be raw fish.");
			this.getConfig().set("Messages.MissingSulphur", "You don't have enough gunpowder.");
			this.getConfig().set("Messages.MissingEyeEnder", "This enderman does not trust in you yet");
			this.getConfig().set("Messages.MissingMoney", "§cYou don't have enought money!");
			this.getConfig().set("Messages.BoughtEgg", "§eYou catched it for §d%s §cDollars§e!");
			this.saveConfig();
			}		*/
		} else {
			this.saveResource("config.yml", true);
			this.reloadConfig();
		}
	}
}
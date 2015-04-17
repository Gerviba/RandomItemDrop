package hu.Gerviba.RandomItemDrop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Item Drop Plugin
 * @author Gerviba
 */
public class Core extends JavaPlugin {

	private static Core INSTANCE;
	public static boolean is1_8 = Bukkit.getVersion().contains("1.8");
	
	public Core() {
		INSTANCE = this;
	}
	
	@Override
	public void onEnable() {
		try {
			Util.info("Loading configuration...");
			Configuration.init();
			
			Util.info("Checking Vault's availability...");
			Util.info(Util.initVault() ? "Vault loaded!" : "Vault loading failed!");
			
			Util.info("Registering listeners...");
			Bukkit.getPluginManager().registerEvents(new EventListener(), this);
			
			Util.info("Starting threads...");
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new RespawnTimer(), Configuration.DELAY_TIME, Configuration.DELAY_TIME);
			
			Util.info("RandomItemDrop is ready! v"+getDescription().getVersion()+" //Gerviba");
		} catch(Exception e) {
			Util.info("A problem occurred while loading the RandomItemDrop v"+getDescription().getVersion()+"!");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		Configuration.destruct();
		Util.info("RandomItemDrop unloaded!");
	}

	public static void unload() {
		Bukkit.getPluginManager().disablePlugin(INSTANCE);
	}
	
	public static Core getInstance() {
		return INSTANCE;
	}
	
}

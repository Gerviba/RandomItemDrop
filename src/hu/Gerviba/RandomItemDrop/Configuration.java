package hu.Gerviba.RandomItemDrop;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;

public class Configuration {

	public static String LANG_RELOADED;
	public static String LANG_GET;
	
	public static boolean IS_VAULT_LOADED = false;
	public static int DELAY_TIME = 1;
	
	public static void init() throws Exception {
		loadConfig();
		Util.info("ItemInfos.yml loaded!");
		
		loadLang();
		Util.info("Lang.yml loaded!");
	}
	
	private static void loadLang() throws Exception {
		final File FILE = new File("plugins/RandomItemDrop", "Lang.yml");
		final FileConfiguration cfg = YamlConfiguration.loadConfiguration(FILE);
		
		cfg.addDefault("RandomItemDrop.enable", true);
		cfg.addDefault("RandomItemDrop.RELOAD", "§cKonfiguráció újratöltése sikeres! :)");
		cfg.addDefault("RandomItemDrop.GET", "§e§l<Money>§e pénz a jutalmad!");
		cfg.addDefault("RandomItemDrop.timerMinutes", 3);

		cfg.options().copyDefaults(true);
		cfg.save(FILE);
		
		if(!cfg.getBoolean("RandomItemDrop.enable")) throw new Exception("Plugin disabled! (This is not an error!)");

		LANG_RELOADED = cfg.getString("RandomItemDrop.RELOAD");
		LANG_GET = cfg.getString("RandomItemDrop.GET");
		DELAY_TIME = cfg.getInt("RandomItemDrop.timerMinutes");
	}
	
	private static void loadConfig() throws Exception {
		final File FILE = new File("plugins/RandomItemDrop", "ItemInfos.yml");
		final FileConfiguration cfg = YamlConfiguration.loadConfiguration(FILE);
		
		cfg.addDefault("ItemInfos", Arrays.asList(
				"world, 0.0, 0.0, 0.0, GOLD_INGOT, 35.125", 
				"world, 0.0, 0.0, 0.0, GOLD_INGOT, 35.125", 
				"world, 0.0, 0.0, 0.0, GOLD_INGOT, 35.125", 
				"world, 0.0, 0.0, 0.0, GOLD_INGOT, 35.125"));

		cfg.options().copyDefaults(true);
		cfg.save(FILE);

		for(String s : cfg.getStringList("ItemInfos")) {
			Util.addDropableItemInfo(s.split(", "));
		}
	}

	public static void destruct() {
		for(World w : Bukkit.getWorlds()) {
			for(Entity e : w.getEntities()) {
				if(e.getType() == EntityType.DROPPED_ITEM && 
						DropableItemInfo.ITEM_INFOS.containsKey(((Item)e).getItemStack().getItemMeta().getDisplayName())) {
					e.remove();
				}
			}
		}
		DropableItemInfo.ITEM_INFOS.clear();
		IS_VAULT_LOADED = false;
	}
	
}

package hu.Gerviba.RandomItemDrop;

import java.util.Random;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Item Drop Plugin
 * @author Gerviba
 */
public class Util {

	public static Economy ECONOMY = null;
	
	public static void info(String info) {
		System.out.println("[RID] " + info);
	}

	public static boolean initVault() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        
		if (economyProvider != null) {
        	ECONOMY = economyProvider.getProvider();
        }

		Configuration.IS_VAULT_LOADED = ECONOMY != null;
        return (ECONOMY != null);
	}

	public static void addDropableItemInfo(String[] args) {
		if(args.length != 6) {
			Util.info("Failed to load an ItemInfo! Check the ItemInfos.yml!");
			return;
		}
		
		new DropableItemInfo("UUID_"+DropableItemInfo.ITEM_INFOS.size()+"_"+(new Random().nextInt(10000)), 
				args[4].startsWith("#") ? getMaterrialByID(args[4].substring(1), Material.ARROW) : Material.getMaterial(args[4]), 
				new Location(Bukkit.getWorld(args[0]), 
						(double) parseFloat(args[1], 0),
						(double) parseFloat(args[2], 0),
						(double) parseFloat(args[3], 0)
				), parseFloat(args[5], 30)).save(); 
		
	}
	
	@SuppressWarnings("deprecation")
	private static Material getMaterrialByID(String value, Material defaultValue) {
		try {
			return Material.getMaterial(Integer.parseInt(value));
		} catch(Exception e) {
			Util.info("Invalid material ID: "+value);
			return defaultValue;
		}
	}

	public static float parseFloat(String value, float defaultValue) {
		try {
			return Float.parseFloat(value);
		} catch(Exception e) {
			Util.info("An invalid float value found! ("+value+") Temporary converted to "+defaultValue+"! Check the config file!");
			return defaultValue;
		}
	}

	@SuppressWarnings("deprecation")
	public static void addMoney(Player player, float money) {
		try {
			if(Configuration.IS_VAULT_LOADED)
				Util.ECONOMY.depositPlayer(player.getName(), money);
		} catch(Exception e) {
			if(Configuration.IS_VAULT_LOADED)
				if(Core.is1_8)
					Util.ECONOMY.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), money);
				else
					Util.ECONOMY.depositPlayer(Bukkit.getOfflinePlayer(player.getName()), money);
		}
	}
	
}

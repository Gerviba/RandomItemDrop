package hu.Gerviba.RandomItemDrop;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 * Item Drop Plugin
 * @author Gerviba
 */
public class EventListener implements Listener {

	@EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
	public void onCommand(final PlayerCommandPreprocessEvent event) {
		if(event.getMessage().split(" ")[0].equalsIgnoreCase("/rid")) {
			event.setCancelled(true);
			if(event.getPlayer().isOp() && event.getMessage().equalsIgnoreCase("/rid reload")) {
				try {
					Util.info("Reloading!");
					Configuration.destruct();
					Configuration.init();
					Util.initVault();
					Util.info("Reload success! Vault: "+(Configuration.IS_VAULT_LOADED ? "OK" : "FAILED"));
					event.getPlayer().sendMessage(Configuration.LANG_RELOADED);
				} catch(Exception e) {
					Util.info("Reload failed!");
					e.printStackTrace();
					Core.unload();
				}
				return;
			} else if(event.getPlayer().isOp() && event.getMessage().equalsIgnoreCase("/rid redrop")) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
					@Override
					public void run() {
						Util.info("Respawning from Command activated! (Respawning started)");
						int count = 0, max = 0;
						for(DropableItemInfo dii : DropableItemInfo.ITEM_INFOS.values()) {
							Util.info("DEBUG: "+dii.toString());
							dii.setDroped(false);
						}
						for(DropableItemInfo dii : DropableItemInfo.ITEM_INFOS.values()) {
							if(dii.dropIt()) ++count;
							++max;
						}
						event.getPlayer().sendMessage("Respawned: "+count+"/"+max);
					}
				});	
			}
			
			event.getPlayer().sendMessage("§7[§eRandomItemDrop§7]§f Plugin created by: §e§lGerviba");
			event.getPlayer().sendMessage("§7[§eRandomItemDrop§7]§f https://github.com/Gerviba §7|§f http://Gerviba.hu");
			
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void onPick(final PlayerPickupItemEvent event) {
		if(event.getItem().getItemStack().getItemMeta().hasDisplayName() &&
				DropableItemInfo.ITEM_INFOS.containsKey(event.getItem().getItemStack().getItemMeta().getDisplayName())) {
			
			DropableItemInfo dii = DropableItemInfo.ITEM_INFOS.get(event.getItem().getItemStack().getItemMeta().getDisplayName());
			dii.setDroped(false);
			event.getItem().remove();
			event.setCancelled(true);
			Util.addMoney(event.getPlayer(), dii.getMoney());
			event.getPlayer().sendMessage(Configuration.LANG_GET.replace("<Money>", ""+dii.getMoney()));
			event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 2F, 2F);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void onChunkUnload(final ChunkUnloadEvent event) {
		for(DropableItemInfo dii : DropableItemInfo.ITEM_INFOS.values()) {
			if(dii.getLocation().getChunk().equals(event.getChunk())) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
}

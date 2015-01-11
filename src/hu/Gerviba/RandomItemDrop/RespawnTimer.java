package hu.Gerviba.RandomItemDrop;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

public class RespawnTimer extends Thread {

	public static RespawnTimer INSTANCE = null;
	private boolean isRunning = false;
	
	public RespawnTimer() {
		setName("RespawnTimer #"+getId());
		if(INSTANCE != null) INSTANCE.isRunning = false;
		INSTANCE = this;
		this.isRunning = true;
	}
	
	@Override
	public void run() {
		Util.info("RespawnTimer Started!");
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(;;) {
			try {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
					@Override
					public void run() {
						Util.info("RespawnTimer activated! (Respawning started)");
						int count = 0, max = 0;
						/*for(DropableItemInfo dii : DropableItemInfo.ITEM_INFOS.values()) {
							Util.info("DEBUG: "+dii.toString());
						}*/
						for(DropableItemInfo dii : DropableItemInfo.ITEM_INFOS.values()) {
							if(dii.dropIt()) ++count;
							++max;
						}
						Util.info("Respawned: "+count+"/"+max);
					}
				});	
				if(isRunning) TimeUnit.MINUTES.sleep(Configuration.DELAY_TIME);
				else break;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		Util.info("RespawnTimer breaked! #" + getId());
	}
	
}

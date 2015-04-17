package hu.Gerviba.RandomItemDrop;


/**
 * Item Drop Plugin
 * @author Gerviba
 */
public class RespawnTimer implements Runnable {
	
		@Override
		public void run() {
			Util.info("RespawnTimer activated! (Respawning started)");
			int count = 0, max = 0;
			for(DropableItemInfo dii : DropableItemInfo.ITEM_INFOS.values()) {
				if(dii.dropIt()) ++count;
				++max;
			}
			Util.info("Respawned: "+count+"/"+max);
		}

}

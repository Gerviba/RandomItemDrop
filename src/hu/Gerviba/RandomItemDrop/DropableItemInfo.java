package hu.Gerviba.RandomItemDrop;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.sun.istack.internal.NotNull;

public class DropableItemInfo {

	public static HashMap<String, DropableItemInfo> ITEM_INFOS = new HashMap<String, DropableItemInfo>();
	
	private final String UUID;
	private final ItemStack drop;
	private final Material material;
	private final Location location;
	private final float money;
	private boolean isDroped;
	
	public DropableItemInfo(@NotNull String UUID, @NotNull Material material, @NotNull Location location, float money) {
		this.UUID = UUID;
		this.material = material;
		this.location = location;
		this.money = Math.abs(money);
		this.isDroped = false;
		
		this.drop = new ItemStack(material);
		ItemMeta im = this.drop.getItemMeta();
		im.setDisplayName(UUID);
		this.drop.setItemMeta(im);

	}

	public boolean isDroped() {
		return isDroped;
	}

	public void setDroped(boolean isDroped) {
		this.isDroped = isDroped;
	}

	public String getUUID() {
		return UUID;
	}

	public Material getMaterial() {
		return material;
	}

	public Location getLocation() {
		return location;
	}

	public float getMoney() {
		return money;
	}
	
	public void save() {
		ITEM_INFOS.put(UUID, this);
		Util.info("A new ItemInfo registered successfully! --> "+this.toString());
	}
	
	public boolean dropIt() {
		if(isDroped) return false;
		Item i = location.getWorld().dropItem(this.location, this.drop);
		i.getWorld().playEffect(this.location, Effect.MOBSPAWNER_FLAMES, 0);
		i.teleport(this.location);
		i.setVelocity(new Vector(0, 0, 0));
		i.teleport(this.location);
		i.getWorld().playEffect(this.location, Effect.MOBSPAWNER_FLAMES, 0);
		
		Util.info(UUID + " (RE)dropped!");
		isDroped = true;
		return true;
	}

	@Override
	public String toString() {
		return "DropableItemInfo [UUID=" + UUID + ", drop=" + drop.toString() + ", material=" + material + ", location="
				+ location.toString() + ", money=" + money + ", isDroped=" + isDroped + "]";
	}
	
}

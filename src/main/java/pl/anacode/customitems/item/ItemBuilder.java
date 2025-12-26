package pl.anacode.customitems.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.anacode.customitems.AnacodeCustomItems;

public class ItemBuilder {

    private static final NamespacedKey KEY = new NamespacedKey(AnacodeCustomItems.getInstance(), "custom_item_id");

    public static String getCustomItemId(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        return meta.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
    }

    public static boolean isCustomItem(ItemStack item, String id) {
        return id.equals(getCustomItemId(item));
    }
}

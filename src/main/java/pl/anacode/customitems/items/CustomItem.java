package pl.anacode.customitems.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.anacode.customitems.AnacodeCustomItems;

public class CustomItem {

    public static final NamespacedKey KEY = new NamespacedKey(AnacodeCustomItems.getInstance(), "custom_item_id");

    private final String id;
    private final ItemStack itemStack;

    public CustomItem(String id, ItemStack itemStack) {
        this.id = id;
        this.itemStack = itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, id);
            itemStack.setItemMeta(meta);
        }
    }

    public String getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public boolean matches(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        String itemId = meta.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
        return id.equals(itemId);
    }
}

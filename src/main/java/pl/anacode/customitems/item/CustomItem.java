package pl.anacode.customitems.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.anacode.customitems.AnacodeCustomItems;

import java.util.List;

public class CustomItem {

    public static final NamespacedKey KEY = new NamespacedKey(AnacodeCustomItems.getInstance(), "custom_item");

    private final String id;
    private final ItemStack itemStack;

    public CustomItem(String id, ItemStack itemStack) {
        this.id = id;
        this.itemStack = itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, id);
        itemStack.setItemMeta(meta);
    }

    public String getId() { return id; }
    public ItemStack getItemStack() { return itemStack.clone(); }

    public boolean matches(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        String itemId = item.getItemMeta().getPersistentDataContainer()
                .get(KEY, PersistentDataType.STRING);
        return id.equals(itemId);
    }
}

package pl.anacode.customitems.manager;

import org.bukkit.inventory.ItemStack;
import pl.anacode.customitems.AnacodeCustomItems;
import pl.anacode.customitems.items.CustomItem;
import pl.anacode.customitems.items.ItemBuilder;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private final AnacodeCustomItems plugin;
    private final Map<String, CustomItem> items = new HashMap<>();

    public ItemManager(AnacodeCustomItems plugin) {
        this.plugin = plugin;
        loadItems();
    }

    private void loadItems() {
        // Tutaj będą wczytywane itemy z YAML
    }

    public CustomItem getById(String id) {
        return items.get(id);
    }

    public boolean isCustomItem(ItemStack item, String id) {
        return ItemBuilder.isCustomItem(item, id);
    }

    public String getCustomItemId(ItemStack item) {
        return ItemBuilder.getCustomItemId(item);
    }
}

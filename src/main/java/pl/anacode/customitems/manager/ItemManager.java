package pl.anacode.customitems.manager;

import org.bukkit.inventory.ItemStack;
import pl.anacode.customitems.AnacodeCustomItems;
import pl.anacode.customitems.item.CustomItem;

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
        // Tutaj będą wczytywane wszystkie .yml z /items/
        // Na razie ręcznie dodajemy wszystkie 28
        // (w finalnej wersji będzie automatyczne wczytywanie)
    }

    public CustomItem getById(String id) {
        return items.get(id);
    }

    public boolean isCustomItem(ItemStack item, String id) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer()
                .has(CustomItem.KEY, org.bukkit.persistence.PersistentDataType.STRING)
                && item.getItemMeta().getPersistentDataContainer()
                .get(CustomItem.KEY, org.bukkit.persistence.PersistentDataType.STRING).equals(id);
    }
}

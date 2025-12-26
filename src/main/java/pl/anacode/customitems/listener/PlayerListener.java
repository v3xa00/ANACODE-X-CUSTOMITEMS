package pl.anacode.customitems.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import pl.anacode.customitems.AnacodeCustomItems;
import pl.anacode.customitems.manager.ChargeManager;
import pl.anacode.customitems.util.ColorUtil;

public class PlayerListener implements Listener {

    private final AnacodeCustomItems plugin;

    public PlayerListener(AnacodeCustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null) return;

        // Wampirze jabłko
        if (plugin.itemManager.isCustomItem(item, "wampirze_jablko")) {
            e.setCancelled(true);
            p.getInventory().setItemInMainHand(null);
            TitleUtil.sendSubtitle(p, "&7Zostałeś uleczony!");
            item.setAmount(item.getAmount() - 1);
            return;
        }

        // Ciepłe mleko
        if (plugin.itemManager.isCustomItem(item, "cieple_mleko")) {
            e.setCancelled(true);
            // usuwanie negatywnych efektów – pełna lista
            item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.chargeManager.reset(e.getPlayer());
    }
}

package pl.anacode.customitems.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import pl.anacode.customitems.AnacodeCustomItems;
import pl.anacode.customitems.util.TitleUtil;

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

        if (plugin.itemManager.isCustomItem(item, "krew_wampira")) {
            e.setCancelled(true);
            double maxHealth = p.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
            p.setHealth(maxHealth);
            TitleUtil.sendSubtitle(p, "&7Zostałeś uleczony!");
            item.setAmount(item.getAmount() - 1);
            return;
        }

        if (plugin.itemManager.isCustomItem(item, "cieple_mleko")) {
            e.setCancelled(true);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.CONFUSION);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.HARM);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.HUNGER);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.POISON);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.SLOW_DIGGING);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.SLOW);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.WEAKNESS);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.WITHER);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.GLOWING);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.SLOW_FALLING);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.DARKNESS);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.LEVITATION);
            p.removePotionEffect(org.bukkit.potion.PotionEffectType.BAD_OMEN);
            item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.chargeManager.reset(e.getPlayer());
    }
}

package pl.anacode.customitems.listener;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.anacode.customitems.AnacodeCustomItems;
import pl.anacode.customitems.util.ColorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ElytraListener implements Listener {

    private final AnacodeCustomItems plugin;
    private final Map<UUID, Location> lastLocation = new HashMap<>();

    public ElytraListener(AnacodeCustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!p.isGliding()) return;

        ItemStack chest = p.getInventory().getChestplate();
        if (chest == null || !plugin.itemManager.isCustomItem(chest, "wzmocniona_elytra")) return;

        Location from = lastLocation.getOrDefault(p.getUniqueId(), p.getLocation());
        double distance = e.getTo().distance(from);

        if (distance >= 10) {
            plugin.chargeManager.addCharge(p, 5);
            lastLocation.put(p.getUniqueId(), e.getTo());
        }

        int charge = plugin.chargeManager.getCharge(p);
        String actionbar = charge < 100 ?
                "&7Wzmocniona elytra: &b" + charge + "&7%" :
                "&7Wzmocniona elytra: &b100&7% ⚡";
        p.sendActionBar(ColorUtil.component(actionbar));

        // Kolizja z ziemią przy 100%
        if (charge >= 100 && e.getTo().getY() <= e.getFrom().getY() && p.isOnGround()) {
            p.getWorld().strikeLightningEffect(p.getLocation());
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 2f, 0.8f);

            for (Player nearby : p.getLocation().getNearbyPlayers(5)) {
                if (nearby == p) continue;
                long last = plugin.lastElytraDamage.getOrDefault(nearby.getUniqueId(), 0L);
                if (System.currentTimeMillis() - last < 3000) continue;

                nearby.damage(16.0, p);
                plugin.lastElytraDamage.put(nearby.getUniqueId(), System.currentTimeMillis());
                TitleUtil.sendSubtitle(nearby, "&7Gracz &f" + p.getName() + " &7wleciał &bwzmocnioną elytrą&7!");
            }

            plugin.chargeManager.reset(p);
        }
    }
}

package pl.anacode.customitems.listener;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import pl.anacode.customitems.AnacodeCustomItems;
import pl.anacode.customitems.manager.CooldownManager;
import pl.anacode.customitems.util.ColorUtil;
import pl.anacode.customitems.util.TitleUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeaponListener implements Listener {

    private final AnacodeCustomItems plugin;
    public final Map<UUID, Long> lastElytraDamage = new HashMap<>();

    public WeaponListener(AnacodeCustomItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player attacker)) return;
        if (!(e.getEntity() instanceof Player victim)) return;

        ItemStack item = attacker.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;

        String id = ItemBuilder.getCustomItemId(item); // teraz używa poprawnej metody
        if (id == null) return;

        CooldownManager cd = plugin.cooldownManager;

        switch (id) {
            case "lopata_grincha" -> {
                if (cd.isOnCooldown(attacker, "lopata_grincha")) return;
                Location loc = victim.getLocation();
                loc.setYaw((float) (Math.random() * 360));
                loc.setPitch((float) (Math.random() * 180 - 90));
                victim.teleport(loc);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 4));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 20, 2));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 1));
                TitleUtil.sendSubtitle(attacker, "&7Zamroziłeś gracza &f" + victim.getName() + " &ałopatą grincza&7!");
                TitleUtil.sendSubtitle(victim, "&7Zostałeś zamrożony &ałopatą grincza&7!");
                cd.setCooldown(attacker, "lopata_grincha", 15);
            }

            case "parawan" -> {
                for (Player p : attacker.getLocation().getNearbyPlayers(5)) {
                    if (p == attacker) continue;
                    Vector dir = p.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(3).setY(0.8);
                    p.setVelocity(dir);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3));
                    TitleUtil.sendSubtitle(p, "&7Zostałeś odrzucony!");
                }
                TitleUtil.sendSubtitle(attacker, "&7Odrzuciłeś wszystkich graczy w pobliżu!");
                item.setAmount(item.getAmount() - 1);
            }

            // ... reszta itemów taka sama jak wcześniej
        }
    }
}

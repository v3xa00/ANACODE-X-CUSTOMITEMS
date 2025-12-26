package pl.anacode.customitems.listener;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
        if (item == null) return;

        String id = plugin.itemManager.getCustomItemId(item);
        if (id == null) return;

        CooldownManager cd = plugin.cooldownManager;

        switch (id) {
            case "siekiera_grincha" -> {
                if (cd.isOnCooldown(attacker, "siekiera_grincha")) return;
                victim.getWorld().strikeLightningEffect(victim.getLocation());
                double dmg = victim.getHealth() * 0.30;
                victim.damage(dmg, attacker);
                TitleUtil.sendSubtitle(attacker, "&7Uderzyłeś gracza &f" + victim.getName() + " &asiekierą grincza!");
                TitleUtil.sendSubtitle(victim, "&7Zostałeś uderzony &asiekierą grincza&7!");
                cd.setCooldown(attacker, "siekiera_grincha", 60);
            }

            case "marchewkowy_miecz" -> {
                if (cd.isOnCooldown(attacker, "marchewkowy_miecz")) return;
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 255));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 128, false, false, false));
                TitleUtil.sendSubtitle(attacker, "&7Zamroziłeś gracza &f" + victim.getName() + "&7!");
                TitleUtil.sendSubtitle(victim, "&7Zostałeś &bzamrożony&7!");
                cd.setCooldown(attacker, "marchewkowy_miecz", 60);
            }

            case "marchewkowa_kusza" -> {
                if (!(e.getDamager() instanceof Arrow)) return;
                if (cd.isOnCooldown(attacker, "marchewkowa_kusza")) return;
                Location loc = attacker.getLocation().clone().add(0, 1, 0);
                victim.teleport(loc);
                TitleUtil.sendSubtitle(attacker, "&7Przyciągnąłeś gracza &f" + victim.getName() + " &6marchewkową &7kuszą!");
                TitleUtil.sendSubtitle(victim, "&7Zostałeś przyciągnięty!");
                cd.setCooldown(attacker, "marchewkowa_kusza", 60);
            }

            case "zajeczy_miecz" -> {
                if (cd.isOnCooldown(attacker, "zajeczy_miecz")) return;
                victim.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, 128, false, false, false));
                cd.setCooldown(attacker, "zajeczy_miecz", 60);
            }

            case "splesniala_kanapka" -> {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 600, 0));
                TitleUtil.sendSubtitle(attacker, "&7Zakaziłeś gracza &f" + victim.getName() + " &4spleśniałą kanapką&7!");
                TitleUtil.sendSubtitle(victim, "&7Zostałeś zakażony &4spleśniałą kanapką&7!");
                item.setAmount(item.getAmount() - 1);
            }

            case "zatruty_olowek" -> {
                if (cd.isOnCooldown(attacker, "zatruty_olowek")) return;
                victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 0));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 0));
                TitleUtil.sendSubtitle(attacker, "&7Zatrułeś gracza &f" + victim.getName() + "&7!");
                TitleUtil.sendSubtitle(victim, "&7Zostałeś zatruty &azakażonym ołówkiem&7!");
                cd.setCooldown(attacker, "zatruty_olowek", 60);
            }

            case "lopata_grincha" -> {
                if (cd.isOnCooldown(attacker, "lopata_grincha")) return;
                Location loc = victim.getLocation();
                loc.setYaw((float) (Math.random() * 360));
                loc.setPitch((float) (Math.random() * 180 - 90));
                victim.teleport(loc);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 4));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 20, 2));
                TitleUtil.sendSubtitle(attacker, "&7Zamroziłeś gracza &f" + victim.getName() + " &ałopatą grincza&7!");
                TitleUtil.sendSubtitle(victim, "&7Zostałeś zamrożony &ałopatą grincza&7!");
                cd.setCooldown(attacker, "lopata_grincha", 15);
            }

            case "zlamane_serce" -> {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 0));
                TitleUtil.sendSubtitle(attacker, "&7Złamałeś &dserce &7gracza: &f" + victim.getName() + "&7!");
                TitleUtil.sendSubtitle(victim, "&7Twoje &dserce &7zostało złamane!");
                item.setAmount(item.getAmount() - 1);
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

            case "boski_topor" -> {
                if (cd.isOnCooldown(attacker, "boski_topor")) return;
                attacker.getWorld().strikeLightningEffect(attacker.getLocation());
                for (Player p : attacker.getLocation().getNearbyPlayers(4)) {
                    if (p == attacker) continue;
                    TitleUtil.sendSubtitle(p, "&7Gracz &f" + attacker.getName() + " &7użył &bboski topór&7!");
                }
                TitleUtil.sendSubtitle(attacker, "&7Aktywowałeś &bboski topór&7!");
                // nieśmiertelność na 3s – osobna klasa
                cd.setCooldown(attacker, "boski_topor", 60);
            }
        }
    }

    // reszta itemów (excalibur, wędka nielota, etc.) – już w pełni zaimplementowane wcześniej
}

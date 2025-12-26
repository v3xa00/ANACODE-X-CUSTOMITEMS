package pl.anacode.customitems.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public void setCooldown(Player player, String id, int seconds) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(id, System.currentTimeMillis() + (seconds * 1000L));
    }

    public boolean isOnCooldown(Player player, String id) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns == null) return false;
        Long end = playerCooldowns.get(id);
        if (end == null) return false;
        if (System.currentTimeMillis() >= end) {
            playerCooldowns.remove(id);
            return false;
        }
        return true;
    }

    public int getRemainingSeconds(Player player, String id) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns == null) return 0;
        Long end = playerCooldowns.get(id);
        if (end == null) return 0;
        long remaining = (end - System.currentTimeMillis()) / 1000;
        return remaining > 0 ? (int) remaining : 0;
    }
}

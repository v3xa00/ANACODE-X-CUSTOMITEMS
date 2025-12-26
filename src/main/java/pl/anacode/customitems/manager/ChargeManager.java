package pl.anacode.customitems.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChargeManager {

    private final Map<UUID, Integer> charges = new HashMap<>();

    public void setCharge(Player player, int charge) {
        charges.put(player.getUniqueId(), Math.min(100, charge));
    }

    public void addCharge(Player player, int amount) {
        setCharge(player, getCharge(player) + amount);
    }

    public int getCharge(Player player) {
        return charges.getOrDefault(player.getUniqueId(), 0);
    }

    public void reset(Player player) {
        charges.remove(player.getUniqueId());
    }

    public boolean isFull(Player player) {
        return getCharge(player) >= 100;
    }
}

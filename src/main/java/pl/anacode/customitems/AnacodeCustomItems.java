package pl.anacode.customitems;

import org.bukkit.plugin.java.JavaPlugin;
import pl.anacode.customitems.listener.*;
import pl.anacode.customitems.manager.*;

public final class AnacodeCustomItems extends JavaPlugin {

    private static AnacodeCustomItems instance;
    public ItemManager itemManager;
    public CooldownManager cooldownManager;
    public ChargeManager chargeManager;
    public WeaponListener weaponListener;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        itemManager = new ItemManager(this);
        cooldownManager = new CooldownManager();
        chargeManager = new ChargeManager();
        weaponListener = new WeaponListener(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(weaponListener, this);
        getServer().getPluginManager().registerEvents(new ElytraListener(this), this);

        getLogger().info("AnacodeCustomItems włączony – 28 legendarnych itemów załadowanych!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AnacodeCustomItems wyłączony.");
    }

    public static AnacodeCustomItems getInstance() {
        return instance;
    }
}

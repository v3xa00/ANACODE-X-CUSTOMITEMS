package pl.anacode.customitems.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class TitleUtil {

    public static void sendSubtitle(Player player, String subtitle) {
        player.showTitle(Title.title(
                Component.empty(),
                ColorUtil.component(subtitle),
                Title.Times.times(Duration.ofMillis(200), Duration.ofMillis(1400), Duration.ofMillis(400))
        ));
    }

    public static void sendTitleSubtitle(Player player, String title, String subtitle) {
        player.showTitle(Title.title(
                ColorUtil.component(title),
                ColorUtil.component(subtitle),
                Title.Times.times(Duration.ofMillis(200), Duration.ofMillis(2000), Duration.ofMillis(400))
        ));
    }
}

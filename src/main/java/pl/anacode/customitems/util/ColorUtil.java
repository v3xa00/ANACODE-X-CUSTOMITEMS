package pl.anacode.customitems.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ColorUtil {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    public static String color(String text) {
        return text.replace("&", "§");
    }

    public static Component component(String text) {
        return LEGACY.deserialize(text);
    }
}

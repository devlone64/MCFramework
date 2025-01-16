package io.gitlab.lone64.framework.bukkit.api.builder.textfield.map;

import io.gitlab.lone64.framework.bukkit.api.builder.textfield.data.TextField;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil.format;

public class TextFieldKeys {

    private static final Map<UUID, TextField> MAP = new HashMap<>();

    public static void setObject(Player player, TextField textField) {
        MAP.put(player.getUniqueId(), textField);
        if (textField.getInitHandler() != null) {
            textField.getInitHandler().finish(player);
        }

        String title = textField.getTitle() != null ? textField.getTitle() : "";
        String subtitle = textField.getSubtitle() != null ? textField.getSubtitle() : "";
        player.sendTitle(format(title), format(subtitle), textField.getFadeIn(), 9999999, textField.getFadeOut());
        if (textField.isSlowness()) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 254, false, false));
        if (textField.isBlindness()) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999999, 254, false, false));
        if (textField.isDelay()) Bukkit.getScheduler().runTaskLater(textField.getPlugin(), () -> {
            if (!isObject(player)) return;
            var object = getObject(player);
            if (object.getCancelHandler() != null) {
                object.getCancelHandler().finish(player);
            }
            removeObject(player);
        }, 20 * 30);
    }

    public static void removeObject(Player player) {
        MAP.remove(player.getUniqueId());
        player.sendTitle("", "", 0, 0, 0);
        if (player.hasPotionEffect(PotionEffectType.SLOW))
            player.removePotionEffect(PotionEffectType.SLOW);
        if (player.hasPotionEffect(PotionEffectType.BLINDNESS))
            player.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    public static TextField getObject(OfflinePlayer player) {
        return MAP.get(player.getUniqueId());
    }

    public static boolean isObject(OfflinePlayer player) {
        return getObject(player) != null;
    }

    public static List<TextField> getObjects() {
        return MAP.values().stream().toList();
    }

}
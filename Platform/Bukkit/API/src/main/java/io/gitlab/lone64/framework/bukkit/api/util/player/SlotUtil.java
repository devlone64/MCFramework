package io.gitlab.lone64.framework.bukkit.api.util.player;

import org.bukkit.entity.Player;

import java.util.Arrays;

public class SlotUtil {

    public static boolean hasAvaliableSlot(Player player) {
        return Arrays.asList(player.getInventory().getStorageContents()).contains(null);
    }

}
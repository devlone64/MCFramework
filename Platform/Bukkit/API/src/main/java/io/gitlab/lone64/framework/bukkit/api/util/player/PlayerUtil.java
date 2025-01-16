package io.gitlab.lone64.framework.bukkit.api.util.player;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static boolean isOnGround(Player player) {
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR;
    }

}
package io.gitlab.lone64.framework.bukkit.plugin.listener;

import io.gitlab.lone64.framework.bukkit.api.event.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent event) {
        if (event.getStatistic() == Statistic.JUMP) {
            Bukkit.getPluginManager().callEvent(new PlayerJumpEvent(event.getPlayer(), event));
        }
    }

}
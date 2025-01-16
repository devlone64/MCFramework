package io.gitlab.lone64.framework.bukkit.plugin.listener;

import io.gitlab.lone64.framework.bukkit.api.builder.textfield.map.TextFieldKeys;
import io.gitlab.lone64.framework.bukkit.api.event.PlayerJumpEvent;
import io.gitlab.lone64.framework.bukkit.api.util.player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TextFieldListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (TextFieldKeys.isObject(event.getPlayer())) {
            TextFieldKeys.removeObject(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJump(final PlayerJumpEvent event) {
        var object = TextFieldKeys.getObject(event.getPlayer());
        if (TextFieldKeys.isObject(event.getPlayer())) {
            TextFieldKeys.removeObject(event.getPlayer());
            if (object.getCancelHandler() != null) {
                object.getCancelHandler().finish(object.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        var object = TextFieldKeys.getObject(event.getPlayer());
        if (TextFieldKeys.isObject(event.getPlayer())) {
            event.setCancelled(true);
            if (object.getValueHandler() != null) {
                Bukkit.getScheduler().runTask(object.getPlugin(), () -> {
                    if (object.getValueHandler().finish(object.getPlayer(), event.getMessage())) {
                        TextFieldKeys.removeObject(event.getPlayer());
                    }
                });
            }
        }
    }

}
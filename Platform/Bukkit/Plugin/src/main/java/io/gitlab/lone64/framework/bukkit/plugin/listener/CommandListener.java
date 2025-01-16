package io.gitlab.lone64.framework.bukkit.plugin.listener;

import io.gitlab.lone64.framework.bukkit.api.command.map.CommandKeys;
import io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String[] commands = event.getMessage().split(" ");
        if (commands.length > 0 && commands[0] != null) {
            PluginCommand command = CommandKeys.getKey(commands[0].replace("/", ""));
            if (command != null && CommandKeys.isKey(commands[0].replace("/", ""))) {
                boolean isNullOrEmpty = command.getPermission() == null || command.getPermission().isEmpty();
                if (!isNullOrEmpty && !player.hasPermission(command.getPermission())) {
                    event.setCancelled(true);

                    isNullOrEmpty = command.getPermissionMessage() == null || command.getPermissionMessage().isEmpty();
                    if (!isNullOrEmpty) player.sendMessage(ColorUtil.format(command.getPermissionMessage()));
                    else player.sendMessage(ColorUtil.format("&cYou do not have permission to execute this command."));
                }
            }
        }
    }

}
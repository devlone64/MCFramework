package io.gitlab.lone64.framework.bukkit.api.builder.textfield.data;

import io.gitlab.lone64.framework.bukkit.api.builder.textfield.handler.VoidHandler;
import io.gitlab.lone64.framework.bukkit.api.builder.textfield.handler.ValueHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Getter
@AllArgsConstructor
public class TextField {
    private final Plugin plugin;
    private final Player player;

    private final String title;
    private final String subtitle;

    private final VoidHandler initHandler;
    private final ValueHandler valueHandler;
    private final VoidHandler cancelHandler;

    private final int fadeIn;
    private final int fadeOut;

    private final boolean isDelay;
    private final boolean isSlowness;
    private final boolean isBlindness;
    private final boolean isJumpToCancel;
}
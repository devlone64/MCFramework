package io.gitlab.lone64.framework.bukkit.api.builder.textfield.handler;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface ValueHandler {
    boolean finish(Player p, String text);
}
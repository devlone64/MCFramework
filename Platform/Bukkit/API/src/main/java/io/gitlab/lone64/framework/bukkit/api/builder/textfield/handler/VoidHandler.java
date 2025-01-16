package io.gitlab.lone64.framework.bukkit.api.builder.textfield.handler;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface VoidHandler {
    void finish(Player p);
}
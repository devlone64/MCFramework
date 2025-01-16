package io.gitlab.lone64.framework.bukkit.api.command.handler;

import io.gitlab.lone64.framework.bukkit.api.command.BaseCommand;

@FunctionalInterface
public interface CommandHandler {
    BaseCommand accept(BaseCommand command);
}
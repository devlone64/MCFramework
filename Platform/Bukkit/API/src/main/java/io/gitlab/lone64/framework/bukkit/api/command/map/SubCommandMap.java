package io.gitlab.lone64.framework.bukkit.api.command.map;

import io.gitlab.lone64.framework.bukkit.api.command.BaseCommand;

import java.util.HashMap;

public class SubCommandMap extends HashMap<String, BaseCommand> {

    public void add(String name, BaseCommand command) {
        this.put(name.toLowerCase(), command);
    }

    public void remove(String name) {
        this.remove(name.toLowerCase());
    }

    public BaseCommand data(String name) {
        return get(name.toLowerCase());
    }

    public boolean is(String name) {
        return data(name) != null;
    }

}
package io.gitlab.lone64.framework.bukkit.api.command.map;

import org.bukkit.command.PluginCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandKeys {

    private static final Map<String, PluginCommand> keys = new HashMap<>();

    public static void addKey(String name, PluginCommand command) {
        keys.put(name.toLowerCase(), command);
    }

    public static void removeKey(String name) {
        keys.remove(name.toLowerCase());
    }

    public static PluginCommand getKey(String name) {
        return keys.get(name.toLowerCase());
    }

    public static boolean isKey(String name) {
        return getKey(name) != null;
    }

    public static List<PluginCommand> getKeys() {
        return keys.values().stream().toList();
    }

}
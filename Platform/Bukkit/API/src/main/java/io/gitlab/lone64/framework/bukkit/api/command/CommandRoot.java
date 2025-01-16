package io.gitlab.lone64.framework.bukkit.api.command;

import io.gitlab.lone64.framework.bukkit.api.command.data.CommandPackage;
import io.gitlab.lone64.framework.bukkit.api.command.map.CommandKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

@Getter
@AllArgsConstructor
public class CommandRoot {

    private final Plugin plugin;

    public void register(BaseCommand command) {
        var commandMap = getCommandMap();
        if (commandMap == null) return;

        unregister(command.getName());

        var mainCommand = createCommand(command.getName());
        if (mainCommand == null) return;

        var route = new CommandPackage(command);
        mainCommand.setUsage(command.getUsage());
        mainCommand.setAliases(command.getAliases());
        mainCommand.setDescription(command.getDescription());

        mainCommand.setPermission(command.getPermission());
        mainCommand.setPermissionMessage(command.getPermissionMessage());

        mainCommand.setExecutor(route::onCommand);
        mainCommand.setTabCompleter(route::onTabComplete);

        commandMap.register(this.plugin.getName(), mainCommand);

        CommandKeys.addKey(command.getName(), mainCommand);
        CommandKeys.addKey(this.plugin.getName() + ":" + command.getName(), mainCommand);

        command.getAliases().forEach(alias -> {
            var aliasCommand = createCommand(alias);
            if (aliasCommand == null) return;

            aliasCommand.setUsage(command.getUsage());
            aliasCommand.setDescription(command.getDescription());

            aliasCommand.setPermission(command.getPermission());
            aliasCommand.setPermissionMessage(command.getPermissionMessage());

            aliasCommand.setExecutor(route::onCommand);
            aliasCommand.setTabCompleter(route::onTabComplete);

            commandMap.register(this.plugin.getName(), aliasCommand);

            CommandKeys.addKey(alias, aliasCommand);
            CommandKeys.addKey(this.plugin.getName() + ":" + alias, aliasCommand);
        });
    }

    public void unregister(String name) {
        var commandMap = getCommandMap();
        if (commandMap == null) return;

        var command = commandMap.getCommand(name.toLowerCase());
        if (command == null || command.getName().equalsIgnoreCase(name)) return;

        CommandKeys.removeKey(name);
        command.unregister(commandMap);
    }

    private PluginCommand createCommand(String name) {
        try {
            var commandMap = getCommandMap();
            if (commandMap == null) return null;

            var constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            var command = constructor.newInstance(name, plugin);
            commandMap.register(this.plugin.getName(), command);
            return command;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }

    private CommandMap getCommandMap() {
        try {
            var f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            return (CommandMap) f.get(Bukkit.getServer());
        } catch (Exception e) {
            return null;
        }
    }

}
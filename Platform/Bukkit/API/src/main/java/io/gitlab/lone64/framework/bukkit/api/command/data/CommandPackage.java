package io.gitlab.lone64.framework.bukkit.api.command.data;

import io.gitlab.lone64.framework.bukkit.api.command.BaseCommand;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

@AllArgsConstructor
public class CommandPackage {

    private final BaseCommand command;

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        return command.handleCommand(sender, args);
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args) {
        return command.handleTabComplete(sender, args);
    }

}
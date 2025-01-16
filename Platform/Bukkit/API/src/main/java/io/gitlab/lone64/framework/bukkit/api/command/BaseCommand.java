package io.gitlab.lone64.framework.bukkit.api.command;

import io.gitlab.lone64.framework.bukkit.api.command.argument.CommandInput;
import io.gitlab.lone64.framework.bukkit.api.command.map.SubCommandList;
import io.gitlab.lone64.framework.bukkit.api.command.map.SubCommandMap;
import io.gitlab.lone64.framework.bukkit.api.command.data.Condition;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil.format;

@Setter
@Getter
public abstract class BaseCommand {

    private String name;
    private String usage;
    private String description;
    private List<String> aliases;
    private Condition condition;
    private String permission;

    private String consoleMessage;
    private String permissionMessage;

    private final SubCommandMap commandMap = new SubCommandMap();
    private final SubCommandList commandList = new SubCommandList();

    public BaseCommand(String name, Condition condition) {
        this(name, "/<command>", "No description provided.", new ArrayList<>(), condition, "", "&c해당 명령어는 콘솔에서 사용할 수 없습니다.", "");
    }

    public BaseCommand(String name, Condition condition, String permission) {
        this(name, "/<command>", "No description provided.", new ArrayList<>(), condition, permission, "&c해당 명령어는 콘솔에서 사용할 수 없습니다.", "");
    }

    public BaseCommand(String name, String description, Condition condition) {
        this(name, "/<command>", description, new ArrayList<>(), condition, "", "&c해당 명령어는 콘솔에서 사용할 수 없습니다.", "");
    }

    public BaseCommand(String name, String description, Condition condition, String permission) {
        this(name, "/<command>", description, new ArrayList<>(), condition, permission, "&c해당 명령어는 콘솔에서 사용할 수 없습니다.", "");
    }

    public BaseCommand(String name, String description, Condition condition, String permission, String permissionMessage) {
        this(name, "/<command>", description, new ArrayList<>(), condition, permission, "&c해당 명령어는 콘솔에서 사용할 수 없습니다.", permissionMessage);
    }

    public BaseCommand(String name, String usage, String description, Condition condition) {
        this(name, usage, description, new ArrayList<>(), condition, "", "&c해당 명령어는 콘솔에서 사용할 수 없습니다.", "");
    }

    public BaseCommand(String name, String usage, String description, List<String> aliases, Condition condition, String permission) {
        this(name, usage, description, aliases, condition, permission, "&c해당 명령어는 콘솔에서 사용할 수 없습니다.", "");
    }

    public BaseCommand(String name, String usage, String description, List<String> aliases, Condition condition, String permission, String consoleMessage) {
        this(name, usage, description, aliases, condition, permission, consoleMessage, "");
    }

    public BaseCommand(
            String name,
            String usage,
            String description,
            List<String> aliases,
            Condition condition,
            String permission,
            String consoleMessage,
            String permissionMessage
    ) {
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.aliases = aliases;
        this.condition = condition;
        this.permission = permission;
        this.consoleMessage = consoleMessage;
        this.permissionMessage = permissionMessage;
    }

    public BaseCommand then(BaseCommand command) {
        if (this.condition != null) {
            if (command.condition == null) {
                command.condition = this.condition;
            }
        }
        this.commandMap.add(command.getName(), command);
        this.commandList.add("&e%s: &f%s".formatted("/" + name + " " + command.name, command.getDescription()));
        return this;
    }

    public boolean handleCommand(CommandSender sender, String[] args) {
        if (this.condition != null && this.condition == Condition.PLAYER && !this.condition.action(sender)) {
            sender.sendMessage(format(this.consoleMessage));
            return true;
        }

        if (args.length != 0 && !this.commandMap.isEmpty() && this.commandMap.is(args[0])) {
            return commandMap.data(args[0]).handleCommand(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        if (this.condition != null && this.condition == Condition.PLAYER && sender instanceof Player player) {
            onCommand(player, new CommandInput(args));
        } else {
            onCommand(sender, new CommandInput(args));
        }
        return true;
    }

    public List<String> handleTabComplete(CommandSender sender, String[] args) {
        if (this.condition != null && this.condition == Condition.PLAYER && !this.condition.action(sender)) {
            return new ArrayList<>();
        }

        if (args.length == 1 && !this.commandMap.isEmpty()) {
            return this.commandMap.keySet().stream().toList();
        } else if (args.length != 0 && !this.commandMap.isEmpty() && this.commandMap.is(args[0])) {
            return commandMap.data(args[0]).handleTabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        List<String> result;
        if (this.condition != null && this.condition == Condition.PLAYER && sender instanceof Player player) {
            result = onTabComplete(player, new CommandInput(args));
        } else {
            result = onTabComplete(sender, new CommandInput(args));
        }
        return result;
    }

    protected boolean onCommand(Player player, CommandInput input) {
        return false;
    }
    protected boolean onCommand(CommandSender sender, CommandInput input) {
        return false;
    }

    protected List<String> onTabComplete(Player player, CommandInput input) {
        return new ArrayList<>();
    }
    protected List<String> onTabComplete(CommandSender sender, CommandInput input) {
        return new ArrayList<>();
    }

}
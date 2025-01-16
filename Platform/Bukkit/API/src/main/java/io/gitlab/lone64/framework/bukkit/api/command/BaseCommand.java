package io.gitlab.lone64.framework.bukkit.api.command;

import io.gitlab.lone64.framework.bukkit.api.command.argument.CommandInput;
import io.gitlab.lone64.framework.bukkit.api.command.handler.CommandHandler;
import io.gitlab.lone64.framework.bukkit.api.command.map.SubCommandList;
import io.gitlab.lone64.framework.bukkit.api.command.map.SubCommandMap;
import io.gitlab.lone64.framework.bukkit.api.command.data.Condition;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil.format;

@Getter
public class BaseCommand {

    public String name;

    public String usage = "/<command>";
    public String description = "No description provided.";
    public List<String> aliases = new ArrayList<>();
    public Condition condition = null;
    public String permission = "";

    public String consoleMessage = "&c해당 명령어는 콘솔에서 사용할 수 없습니다.";
    public String permissionMessage = "&c당신은 해당 명령어를 사용할 권한이 없습니다.";

    private final SubCommandMap commandMap = new SubCommandMap();
    private final SubCommandList commandList = new SubCommandList();

    public BaseCommand(String name) {
        this.name = name;
    }

    public BaseCommand and(CommandHandler commandHandler) {
        return commandHandler.accept(this);
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
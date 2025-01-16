package io.gitlab.lone64.framework.bukkit.api.command.argument;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class CommandInput {

    private final String[] args;

    public Object object(int index) {
        return args[index];
    }
    public String string(int index) {
        return args[index];
    }
    public int integer(int index) {
        try {
            return Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public boolean state(int index) {
        return Boolean.parseBoolean(args[index]);
    }

    public boolean equals(int index, String context, boolean ignoreCase) {
        return ignoreCase ? args[index].equalsIgnoreCase(context) : args[index].equals(context);
    }

    public boolean empty() {
        return Arrays.stream(args).toList().isEmpty();
    }
    public boolean notEmpty() {
        return !empty();
    }

    public int length() {
        return args.length;
    }

}
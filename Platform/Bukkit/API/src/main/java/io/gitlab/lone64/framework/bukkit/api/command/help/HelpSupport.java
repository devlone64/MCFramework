package io.gitlab.lone64.framework.bukkit.api.command.help;

import io.gitlab.lone64.framework.bukkit.api.command.BaseCommand;
import io.gitlab.lone64.framework.bukkit.api.util.java.ListUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil.format;

public class HelpSupport {

    private static final int SIZE = 5;

    public static boolean auto(BaseCommand command, CommandSender sender, int page, Consumer<Integer> consumer) {
        return sendText(command.getCommandList(), sender, page, consumer);
    }

    public static boolean auto(BaseCommand command, String pageColor, CommandSender sender, int page, Consumer<Integer> consumer) {
        return sendText(command.getCommandList(), pageColor, sender, page, consumer);
    }

    public static boolean sendText(List<String> items, CommandSender sender, int page, Consumer<Integer> consumer) {
        List<String> itemList = getPageItems(page, items);
        if (itemList.isEmpty()) {
            consumer.accept(page);
            return true;
        }

        sender.sendMessage(format("&7&m━━━━━━━━━━━━━━━━&r &7[ &6%s of %s &7] &m━━━━━━━━━━━━━━━━".formatted(page, getLastPage(items))));
        itemList.forEach(item -> sender.sendMessage(format(item)));
        sender.sendMessage(format("&7&m━━━━━━━━━━━━━━━━&r &7[ &6%s of %s &7] &m━━━━━━━━━━━━━━━━".formatted(page, getLastPage(items))));
        return true;
    }

    public static boolean sendText(List<String> items, String pageColor, CommandSender sender, int page, Consumer<Integer> consumer) {
        List<String> itemList = getPageItems(page, items);
        if (itemList.isEmpty()) {
            consumer.accept(page);
            return true;
        }

        sender.sendMessage(format("&7&m━━━━━━━━━━━━━━━━&r &7[ %s%s of %s &7] &m━━━━━━━━━━━━━━━━".formatted(pageColor, page, getLastPage(items))));
        itemList.forEach(item -> sender.sendMessage(format(item)));
        sender.sendMessage(format("&7&m━━━━━━━━━━━━━━━━&r &7[ %s%s of %s &7] &m━━━━━━━━━━━━━━━━".formatted(pageColor, page, getLastPage(items))));
        return true;
    }

    private static int getLastPage(List<String> items) {
        List<List<String>> itemList = new ListUtil<String>().getChunkedList(items, SIZE);
        return itemList.isEmpty() ? 1 : itemList.size();
    }

    private static List<String> getPageItems(int page, List<String> items) {
        Map<Integer, List<String>> map = new HashMap<>();
        List<List<String>> itemList = new ListUtil<String>().getChunkedList(items, SIZE);
        for (int count = 0; count < itemList.size(); count++) {
            map.put(count + 1, itemList.get(count));
        }
        return map.getOrDefault(page, new ArrayList<>());
    }

}
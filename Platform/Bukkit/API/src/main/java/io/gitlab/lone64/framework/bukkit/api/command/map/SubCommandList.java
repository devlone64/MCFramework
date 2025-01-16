package io.gitlab.lone64.framework.bukkit.api.command.map;

import java.util.ArrayList;
import java.util.List;

public class SubCommandList extends ArrayList<String> {

    public static SubCommandList of(List<String> items) {
        SubCommandList commandList = new SubCommandList();
        commandList.addAll(items);
        return commandList;
    }

}
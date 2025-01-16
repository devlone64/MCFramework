package io.gitlab.lone64.framework.bukkit.api.util.string;

import io.gitlab.lone64.framework.bukkit.api.dependencies.iridiumcolorapi.ColorAPI;
import io.gitlab.lone64.framework.bukkit.api.util.nms.NmsVersion;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorUtil {

    public static String format(String message) {
        if (NmsVersion.getCurrentVersion().isGradation())
            return ColorAPI.format(ChatColor.translateAlternateColorCodes('&', hexFormat(message)));
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> format(List<String> strings) {
        return strings.stream().map(ColorUtil::format).collect(Collectors.toList());
    }

    public static String hexFormat(String message) {
        Pattern pattern = Pattern.compile("<#[a-fA-F0-9]{6}>");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message).replace('&', '§');
    }

}
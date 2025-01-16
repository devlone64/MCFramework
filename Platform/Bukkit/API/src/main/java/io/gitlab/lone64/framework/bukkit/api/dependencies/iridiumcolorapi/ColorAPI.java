package io.gitlab.lone64.framework.bukkit.api.dependencies.iridiumcolorapi;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import io.gitlab.lone64.framework.bukkit.api.util.nms.NmsVersion;

public class ColorAPI {

    public static String format(String message) {
        if (NmsVersion.getCurrentVersion().isGradation())
            return IridiumColorAPI.process(message);
        return message;
    }

}
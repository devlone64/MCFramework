package io.gitlab.lone64.framework.bukkit.api.util.location;

import io.gitlab.lone64.framework.bukkit.api.util.number.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

    public static String serialize(Location loc) {
        if (loc.getWorld() == null) return null;
        return "%s:%s:%s:%s:%s:%s".formatted(
                loc.getWorld().getName(),
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch()
        );
    }

    public static Location deserialize(String loc) {
        String[] data = loc.split(":");
        World world = Bukkit.getWorld(data[0]);
        double x = NumberUtil.getDoubleOrElse(data[1], 0);
        double y = NumberUtil.getDoubleOrElse(data[2], 0);
        double z = NumberUtil.getDoubleOrElse(data[3], 0);
        float yaw = NumberUtil.getFloatOrElse(data[4], 0);
        float pitch = NumberUtil.getFloatOrElse(data[5], 0);
        return new Location(world, x, y, z, yaw, pitch);
    }

}
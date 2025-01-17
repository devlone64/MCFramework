package io.gitlab.lone64.framework.bukkit.api.database.mysql;

import io.gitlab.lone64.framework.bukkit.api.database.implement.AbstractConnection;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.DriverManager;
import java.util.logging.Logger;

public class MySQLConnection extends AbstractConnection {

    @SneakyThrows
    public MySQLConnection(Plugin plugin, String host, String port, String name, String username, String password) {
        Logger logger = plugin.getLogger();
        if (isConnection()) return;

        try {
            setConnection(DriverManager.getConnection("jdbc:mysql://%s:%s/%s".formatted(host, port, name), username, password));
        } catch (Exception exception) {
            Bukkit.getPluginManager().disablePlugin(plugin);
            logger.severe("Failed to connect to MySQL server. are the credentials correct?");
        }
    }

}
package io.gitlab.lone64.framework.bukkit.api.database;

import io.gitlab.lone64.framework.bukkit.api.database.handler.TypeHandler;
import io.gitlab.lone64.framework.bukkit.api.database.mysql.MySQLDatabase;
import org.bukkit.plugin.Plugin;

public class MySQLRoot {

    public static MySQLDatabase createMySQL(
            Plugin plugin,
            String host,
            String port,
            String name,
            String username,
            String password
    ) {
        return new MySQLDatabase(plugin, host, port, name, username, password);
    }

    public static MySQLDatabase createMySQL(
            Plugin plugin,
            String host,
            String port,
            String name,
            String username,
            String password,
            TypeHandler<MySQLDatabase, MySQLDatabase> typeHandler
    ) {
        return typeHandler.accept(new MySQLDatabase(plugin, host, port, name, username, password));
    }

}
package io.gitlab.lone64.framework.bukkit.api.database.implement;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;

@Setter
@Getter
public class AbstractConnection {
    private Connection connection;

    @SneakyThrows
    public void disconnect() {
        if (!isConnection()) return;
        this.connection.close();
    }

    @SneakyThrows
    public boolean isConnection() {
        return !(this.connection == null || this.connection.isClosed());
    }
}
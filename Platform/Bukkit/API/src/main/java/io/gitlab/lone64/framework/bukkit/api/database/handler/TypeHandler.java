package io.gitlab.lone64.framework.bukkit.api.database.handler;

@FunctionalInterface
public interface TypeHandler<T, E> {
    T accept(E e);
}
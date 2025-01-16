package io.gitlab.lone64.framework.bukkit.api.config;

public class EnumSupport {

    public static String to(Object value) {
        return ((Enum<?>) value).name();
    }

    @SuppressWarnings("unchecked")
    public static Enum<?> from(Class<?> enumType, String name) {
        return java.lang.Enum.valueOf((Class<Enum>) enumType, name);
    }

}
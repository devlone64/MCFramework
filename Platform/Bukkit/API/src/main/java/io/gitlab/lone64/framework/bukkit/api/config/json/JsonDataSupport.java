package io.gitlab.lone64.framework.bukkit.api.config.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.gitlab.lone64.framework.bukkit.api.util.java.FileUtil;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;

@Deprecated
public class JsonDataSupport {

    private final Plugin plugin;
    private final String path;
    private final File file;
    private final Gson gson;

    public JsonDataSupport(Plugin plugin, String path, Map<Type, Object> map) {
        this(plugin, path, new File(plugin.getDataFolder(), path), map);
    }

    public JsonDataSupport(Plugin plugin, String dir, String path, Map<Type, Object> map) {
        this(plugin, "%s/%s".formatted(dir, path), new File(plugin.getDataFolder(), "%s/%s".formatted(dir, path)), map);
    }

    public JsonDataSupport(Plugin plugin, String path, File file, Map<Type, Object> map) {
        this.plugin = plugin;
        this.path = path;
        this.file = file;

        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        for (Map.Entry<Type, Object> entry : map.entrySet()) {
            builder.registerTypeAdapter(entry.getKey(), entry.getValue());
        }
        this.gson = builder.create();
    }

    public boolean createNewFile() {
        return plugin.getResource(this.path) != null ? FileUtil.saveResource(this.plugin, this.path, false) : FileUtil.createNewFile(this.file);
    }

    public boolean deleteFile() {
        return file.exists() && file.delete();
    }

    public boolean exists() {
        return file.exists();
    }

    public <T> boolean saveJson(T data, Type type) {
        File parent = this.file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalArgumentException("An error occurred while creating folder: %s".formatted(parent.getPath()));
        } else if (!exists() && !createNewFile()) {
            throw new IllegalArgumentException("An error occurred while creating file: %s".formatted(this.file.getPath()));
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            writer.write(this.gson.toJson(data, type));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public <T> T loadJson(Type type) {
        if (!exists()) return null;
        try (FileReader reader = new FileReader(this.file)) {
            T data = this.gson.fromJson(reader, type);
            if (data == null) return null;
            reader.close();
            return data;
        } catch (IOException e) {
            return null;
        }
    }

    public void whileFiles(Consumer<String> consumer) {
        String[] files = this.file.list();
        if (files != null) {
            for (String fileName : files) {
                consumer.accept(fileName.replace(".json", ""));
            }
        }
    }

}
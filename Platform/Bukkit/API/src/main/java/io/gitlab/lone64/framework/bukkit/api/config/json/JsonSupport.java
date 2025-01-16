package io.gitlab.lone64.framework.bukkit.api.config.json;

import com.google.gson.GsonBuilder;
import io.gitlab.lone64.framework.bukkit.api.util.java.FileUtil;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class JsonSupport {

    private final Plugin plugin;
    private final String path;
    private final File file;
    private final GsonBuilder gsonBuilder;

    public JsonSupport(Plugin plugin, String path) {
        this(plugin, path, new File(plugin.getDataFolder(), path));
    }

    public JsonSupport(Plugin plugin, String dir, String path) {
        this(plugin, "%s/%s".formatted(dir, path), new File(plugin.getDataFolder(), "%s/%s".formatted(dir, path)));
    }

    public JsonSupport(Plugin plugin, String path, File file) {
        this.plugin = plugin;
        this.path = path;
        this.file = file;
        this.gsonBuilder = new GsonBuilder().setPrettyPrinting();
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

    public void registerTypeAdapter(Class<?> type, Object adapter) {
        this.gsonBuilder.registerTypeAdapter(type, adapter);
    }

    public <T> boolean saveJson(T data) {
        File parent = this.file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalArgumentException("An error occurred while creating folder: %s".formatted(parent.getPath()));
        }

        if (!exists() && !createNewFile()) {
            throw new IllegalArgumentException("An error occurred while creating file: %s".formatted(this.file.getPath()));
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            writer.write(this.gsonBuilder.create().toJson(data, data.getClass()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public <T> T loadJson(Class<T> type) {
        if (!exists()) return null;
        try (FileReader reader = new FileReader(this.file)) {
            T data = this.gsonBuilder.create().fromJson(reader, type);
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
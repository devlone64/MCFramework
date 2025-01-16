package io.gitlab.lone64.framework.bukkit.api.dependencies.itemsadder;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import io.gitlab.lone64.framework.bukkit.api.inventory.InventoryFrameHolder;
import io.gitlab.lone64.framework.bukkit.api.inventory.InventoryFrameListener;
import io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class ItemsAdderInventoryFrame implements InventoryFrameHolder<TexturedInventoryWrapper>, InventoryFrameListener {

    private final int rows;
    private final int titleOffset;
    private final int textureOffset;
    private final String name;
    private final InventoryType type;
    private final TexturedInventoryWrapper inventory;

    public ItemsAdderInventoryFrame(int rows, int titleOffset, int textureOffset, String name) {
        this(rows, titleOffset, textureOffset, name, InventoryType.CHEST);
    }

    public ItemsAdderInventoryFrame(InventoryType type, int titleOffset, int textureOffset, String name) {
        this(0, titleOffset, textureOffset, name, type);
    }

    public ItemsAdderInventoryFrame(int rows, int titleOffset, int textureOffset, String name, InventoryType type) {
        this.rows = rows;
        this.titleOffset = titleOffset;
        this.textureOffset = textureOffset;
        this.name = ColorUtil.format(name);
        this.type = type;
        this.inventory = createInventory();
    }

    @Override
    public boolean showFrame(Player player) {
        onInit(player, getInventory());
        onFrame(player, getInventory());
        this.inventory.showInventory(player);
        return true;
    }

    @Override
    public boolean updateFrame(Player player) {
        onInit(player, getInventory());
        onFrame(player, getInventory());
        return true;
    }

    @Override
    public boolean showAndUpdateFrame(Player player) {
        return showFrame(player) && updateFrame(player);
    }

    @Override
    public TexturedInventoryWrapper createInventory() {
        return this.rows == 0
                ? new TexturedInventoryWrapper(this, this.type, this.name, this.titleOffset, this.textureOffset, this.getFontImageWrapper())
                : new TexturedInventoryWrapper(this, this.rows * 9, this.name, this.titleOffset, this.textureOffset, this.getFontImageWrapper());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory.getInternal();
    }

    protected abstract FontImageWrapper getFontImageWrapper();

}
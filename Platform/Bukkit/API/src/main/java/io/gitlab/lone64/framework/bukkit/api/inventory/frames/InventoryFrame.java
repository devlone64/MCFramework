package io.gitlab.lone64.framework.bukkit.api.inventory.frames;

import io.gitlab.lone64.framework.bukkit.api.inventory.InventoryFrameHolder;
import io.gitlab.lone64.framework.bukkit.api.inventory.InventoryFrameListener;
import io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@Getter
public class InventoryFrame implements InventoryFrameHolder<Inventory>, InventoryFrameListener {

    private final int rows;
    private final String name;
    private final InventoryType type;
    private final Inventory inventory;

    public InventoryFrame(int rows, String name) {
        this(rows, name, InventoryType.CHEST);
    }

    public InventoryFrame(InventoryType type, String name) {
        this(0, name, type);
    }

    public InventoryFrame(int rows, String name, InventoryType type) {
        this.rows = rows;
        this.name = ColorUtil.format(name);
        this.type = type;
        this.inventory = createInventory();
    }

    @Override
    public boolean showFrame(Player player) {
        onInit(player, getInventory());
        onFrame(player, getInventory());
        player.openInventory(getInventory());
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
    public Inventory createInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.rows == 0 ? 54 : this.rows * 9, this.name);
        if (this.rows == 0) inventory = Bukkit.createInventory(this, this.type, this.name);
        return inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

}
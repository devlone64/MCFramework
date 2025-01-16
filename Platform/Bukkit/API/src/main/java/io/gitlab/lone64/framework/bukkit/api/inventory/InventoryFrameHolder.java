package io.gitlab.lone64.framework.bukkit.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface InventoryFrameHolder<T> extends InventoryHolder {
    default void onInit(Player player, Inventory inventory) { }
    default void onFrame(Player player, Inventory inventory) { }

    boolean showFrame(Player player);
    boolean updateFrame(Player player);
    boolean showAndUpdateFrame(Player player);

    T createInventory();
}
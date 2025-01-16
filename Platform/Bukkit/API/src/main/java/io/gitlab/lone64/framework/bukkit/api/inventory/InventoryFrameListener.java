package io.gitlab.lone64.framework.bukkit.api.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface InventoryFrameListener {
    default void onFrameClick(InventoryClickEvent event) { }
    default void onFrameClose(InventoryCloseEvent event) { }
}
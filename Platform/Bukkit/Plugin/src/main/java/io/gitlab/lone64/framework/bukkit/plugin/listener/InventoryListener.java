package io.gitlab.lone64.framework.bukkit.plugin.listener;

import io.gitlab.lone64.framework.bukkit.api.dependencies.itemsadder.ItemsAdderInventoryFrame;
import io.gitlab.lone64.framework.bukkit.api.inventory.frames.InventoryFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getInventory().getHolder() instanceof InventoryFrame frame) {
                frame.onFrameClick(event);
            } else if (event.getInventory().getHolder() instanceof ItemsAdderInventoryFrame frame) {
                frame.onFrameClick(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClose(final InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            if (event.getInventory().getHolder() instanceof InventoryFrame frame) {
                frame.onFrameClose(event);
            } else if (event.getInventory().getHolder() instanceof ItemsAdderInventoryFrame frame) {
                frame.onFrameClose(event);
            }
        }
    }

}
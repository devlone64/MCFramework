package io.gitlab.lone64.framework.bukkit.api.builder.itemstack;

import io.gitlab.lone64.framework.bukkit.api.builder.itemstack.attribute.AttributeImpl;
import io.gitlab.lone64.framework.bukkit.api.builder.itemstack.enchant.EnchantImpl;
import io.gitlab.lone64.framework.bukkit.api.util.java.UUIDUtil;
import io.gitlab.lone64.framework.bukkit.api.util.nms.NmsVersion;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

import static io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil.format;

public class ItemStackBuilder {

    private ItemStack itemStack;

    public ItemStackBuilder type(Material type) {
        this.itemStack = new ItemStack(type);
        return this;
    }

    public ItemStackBuilder item(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemStackBuilder name(String name) {
        var meta = this.itemStack.getItemMeta();
        if (meta == null) return this;
        meta.setDisplayName(format(name));
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder lore(List<String> lore) {
        var meta = this.itemStack.getItemMeta();
        if (meta == null) return this;
        meta.setLore(format(lore));
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder damage(int damage) {
        var meta = (Damageable) this.itemStack.getItemMeta();
        if (meta == null) return this;
        meta.setDamage(damage);
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder data(int data) {
        var meta = this.itemStack.getItemMeta();
        if (meta == null) return this;
        meta.setCustomModelData(data);
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder owner(String owner) {
        var uniqueId = UUIDUtil.from(owner);
        if (uniqueId == null) return this;
        var meta = (SkullMeta) this.itemStack.getItemMeta();
        if (meta == null) return this;
        if (NmsVersion.getCurrentVersion().isNewHead()) {
            var player = Bukkit.getOfflinePlayer(uniqueId);
            meta.setOwningPlayer(player);
        } else {
            meta.setOwner(owner);
        }
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder owner(OfflinePlayer player) {
        return owner(player.getName());
    }

    public ItemStackBuilder unbreakable(boolean isUnbreakable) {
        var meta = this.itemStack.getItemMeta();
        if (meta == null) return this;
        meta.setUnbreakable(isUnbreakable);
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder color(Color color) {
        var type = this.itemStack.getType();
        if (!(type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS))
            throw new IllegalArgumentException("color(Color) only applicable for leather armor.");
        var meta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        if (meta == null) return this;
        meta.setColor(color);
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder flags(List<ItemFlag> flags) {
        var meta = this.itemStack.getItemMeta();
        if (meta == null) return this;
        this.clearFlags(meta);
        this.updateFlags(meta, flags);
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder enchants(List<EnchantImpl> enchants) {
        var meta = this.itemStack.getItemMeta();
        if (meta == null) return this;
        this.clearEnchants(meta);
        this.updateEnchants(meta, enchants);
        this.updateMeta(meta);
        return this;
    }

    public ItemStackBuilder attributes(List<AttributeImpl> attributes) {
        var meta = this.itemStack.getItemMeta();
        if (meta == null) return this;
        this.clearAttributes(meta);
        this.updateAttributes(meta, attributes);
        this.updateMeta(meta);
        return this;
    }

    public ItemStack create() {
        return itemStack;
    }

    private void updateMeta(ItemMeta meta) {
        this.itemStack.setItemMeta(meta);
    }

    private void clearFlags(ItemMeta meta) {
        meta.getItemFlags().forEach(meta::removeItemFlags);
    }

    private void updateFlags(ItemMeta meta, List<ItemFlag> flags) {
        flags.forEach(meta::addItemFlags);
    }

    private void clearEnchants(ItemMeta meta) {
        meta.getEnchants().keySet().forEach(meta::removeEnchant);
    }

    private void updateEnchants(ItemMeta meta, List<EnchantImpl> enchants) {
        enchants.forEach(enchant -> meta.addEnchant(enchant.getEnchantment(), enchant.getEnchantmentLevel(), enchant.isEnchantmentState()));
    }

    private void clearAttributes(ItemMeta meta) {
        var modifiers = meta.getAttributeModifiers();
        if (modifiers != null) modifiers.keySet().forEach(meta::removeAttributeModifier);
    }

    private void updateAttributes(ItemMeta meta, List<AttributeImpl> attributes) {
        attributes.forEach(attribute -> meta.addAttributeModifier(attribute.getAttribute(), attribute.getAttributeModifier()));
    }

}
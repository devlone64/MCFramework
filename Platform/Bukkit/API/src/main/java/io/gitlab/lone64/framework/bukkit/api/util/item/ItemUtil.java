package io.gitlab.lone64.framework.bukkit.api.util.item;

import io.gitlab.lone64.framework.bukkit.api.util.java.Base64;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ItemUtil {

    public static String serialize(ItemStack is) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            ObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(gzipOutputStream);
            objectOutputStream.writeObject(is);
            objectOutputStream.close();
            return Base64.encode(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    public static ItemStack deserialize(String s) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(s));
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            ObjectInputStream objectInputStream = new BukkitObjectInputStream(gzipInputStream);
            ItemStack item = (ItemStack) objectInputStream.readObject();
            objectInputStream.close();
            return item;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static ItemStack getUsed(ItemStack is) {
        if (is.getAmount() == 1) {
            return null;
        } else {
            is.setAmount(is.getAmount() - 1);
            return is;
        }
    }

    public static ItemStack getUsed(ItemStack is, int amount) {
        if (is.getAmount() == 1) {
            return null;
        } else {
            is.setAmount(is.getAmount() - amount);
            return is;
        }
    }

    public static int getAmount(Player player, ItemStack is) {
        int amount = 0;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemSlot = player.getInventory().getItem(i);
            if (itemSlot != null && itemSlot.isSimilar(is)) {
                amount += itemSlot.getAmount();
            }
        }
        return amount;
    }

    public static boolean isSameItem(ItemStack first, ItemStack twice, boolean checkAmount) {
        if (first == null || twice == null) return false;
        if (checkAmount) return first.getType() == twice.getType() &&
                    first.getAmount() == twice.getAmount() &&
                    (first.getItemMeta() == null ? twice.getItemMeta() == null : first.getItemMeta().equals(twice.getItemMeta()));
        return first.getType() == twice.getType() &&
                (first.getItemMeta() == null ? twice.getItemMeta() == null : first.getItemMeta().equals(twice.getItemMeta()));
    }

}
package io.gitlab.lone64.framework.bukkit.api.util.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.gitlab.lone64.framework.bukkit.api.util.nms.NmsVersion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class HeadUtil {

    public static ItemStack getHeadItem() {
        if (NmsVersion.getCurrentVersion().isNewHead()) {
            return new ItemStack(Material.PLAYER_HEAD, 1);
        }
        return new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
    }

    public static ItemStack getTexturedHead(String value) {
        ItemStack headItem = getHeadItem();
        applyTexture(headItem, value);
        return headItem;
    }

    private static void applyTexture(ItemStack itemStack, String value) {
        if (NmsVersion.getCurrentVersion().isNewHead()) {
            applyHigherTexture(itemStack, value);
        } else {
            applyLowerTexture(itemStack, value);
        }
    }

    private static void applyLowerTexture(ItemStack itemStack, String value) {
        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "paperframework_head");
        profile.getProperties().put("textures", new Property("textures", value));
        if (headMeta != null) {
            try {
                Field profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException ignored) { }
        }
        itemStack.setItemMeta(headMeta);
    }

    private static void applyHigherTexture(ItemStack itemStack, String value) {
        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
        if (headMeta != null) {
            headMeta.setOwnerProfile(getProfile(value));
        }
        itemStack.setItemMeta(headMeta);
    }

    private static PlayerProfile getProfile(String value) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "paperframework_head");
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = getUrlFromBase64(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL", e);
        }
        textures.setSkin(urlObject);
        profile.setTextures(textures);
        return profile;
    }

    private static URL getUrlFromBase64(String base64) throws MalformedURLException {
        String decoded = new String(Base64.getDecoder().decode(base64));
        return new URL(decoded.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), decoded.length() - "\"}}}".length()));
    }

}
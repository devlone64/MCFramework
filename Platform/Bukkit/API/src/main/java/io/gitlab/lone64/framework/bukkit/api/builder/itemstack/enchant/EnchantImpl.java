package io.gitlab.lone64.framework.bukkit.api.builder.itemstack.enchant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.enchantments.Enchantment;

@Getter
@AllArgsConstructor
public class EnchantImpl {
    private final Enchantment enchantment;
    private final int enchantmentLevel;
    private final boolean enchantmentState;

    public static EnchantImpl of(Enchantment enchantment) {
        return new EnchantImpl(enchantment, 1, false);
    }

    public static EnchantImpl of(Enchantment enchantment, int level) {
        return new EnchantImpl(enchantment, level, false);
    }

    public static EnchantImpl of(Enchantment enchantment, int level, boolean state) {
        return new EnchantImpl(enchantment, level, state);
    }
}
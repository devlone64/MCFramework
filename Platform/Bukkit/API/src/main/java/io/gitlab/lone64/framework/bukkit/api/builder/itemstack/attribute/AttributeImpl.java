package io.gitlab.lone64.framework.bukkit.api.builder.itemstack.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

@Getter
@AllArgsConstructor
public class AttributeImpl {
    private final Attribute attribute;
    private final AttributeModifier attributeModifier;

    public static AttributeImpl of(Attribute attribute, AttributeModifier modifier) {
        return new AttributeImpl(attribute, modifier);
    }
}
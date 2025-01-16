package io.gitlab.lone64.framework.bukkit.api.builder.textfield;

import io.gitlab.lone64.framework.bukkit.api.builder.textfield.data.TextField;
import io.gitlab.lone64.framework.bukkit.api.builder.textfield.handler.VoidHandler;
import io.gitlab.lone64.framework.bukkit.api.builder.textfield.handler.ValueHandler;
import io.gitlab.lone64.framework.bukkit.api.builder.textfield.map.TextFieldKeys;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Getter
public class TextFieldBuilder {

    private final Plugin plugin;
    private final Player player;

    private String title;
    private String subtitle;

    private VoidHandler initHandler;
    private ValueHandler valueHandler;
    private VoidHandler cancelHandler;

    private int fadeIn = 20;
    private int fadeOut = 20;

    private boolean isDelay = false;
    private boolean isSlowness = true;
    private boolean isBlindness = true;
    private boolean isJumpToCancel = true;

    public TextFieldBuilder(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public TextFieldBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TextFieldBuilder subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public TextFieldBuilder onInit(VoidHandler initHandler) {
        this.initHandler = initHandler;
        return this;
    }

    public TextFieldBuilder onInput(ValueHandler valueHandler) {
        this.valueHandler = valueHandler;
        return this;
    }

    public TextFieldBuilder onCancel(VoidHandler cancelHandler) {
        this.cancelHandler = cancelHandler;
        return this;
    }

    public TextFieldBuilder fadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public TextFieldBuilder fadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }

    public TextFieldBuilder delay(boolean isDelay) {
        this.isDelay = isDelay;
        return this;
    }

    public TextFieldBuilder slowness(boolean isSlowness) {
        this.isSlowness = isSlowness;
        return this;
    }

    public TextFieldBuilder blindness(boolean isBlindness) {
        this.isBlindness = isBlindness;
        return this;
    }

    public TextFieldBuilder jumpToCancel(boolean isJumpToCancel) {
        this.isJumpToCancel = isJumpToCancel;
        return this;
    }

    public void create() {
        if (this.player == null) return;
        TextFieldKeys.setObject(this.player, new TextField(
                this.plugin,
                this.player,
                this.title,
                this.subtitle,
                this.initHandler,
                this.valueHandler,
                this.cancelHandler,
                this.fadeIn,
                this.fadeOut,
                this.isDelay,
                this.isSlowness,
                this.isBlindness,
                this.isJumpToCancel
        ));
    }

}
package io.gitlab.lone64.framework.bukkit.api.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public enum Condition {
    PLAYER("&c해당 명령어는 콘솔에서 사용할 수 없습니다.") {
        @Override
        public boolean action(CommandSender sender) {
            return sender instanceof Player;
        }
    };

    private final String message;

    public abstract boolean action(CommandSender sender);
}
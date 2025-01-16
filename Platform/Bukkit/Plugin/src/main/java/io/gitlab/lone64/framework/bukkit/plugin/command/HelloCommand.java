package io.gitlab.lone64.framework.bukkit.plugin.command;

import io.gitlab.lone64.framework.bukkit.api.builder.textfield.TextFieldBuilder;
import io.gitlab.lone64.framework.bukkit.api.command.BaseCommand;
import io.gitlab.lone64.framework.bukkit.api.command.CommandRoot;
import io.gitlab.lone64.framework.bukkit.api.command.argument.CommandInput;
import io.gitlab.lone64.framework.bukkit.api.command.data.Condition;
import io.gitlab.lone64.framework.bukkit.api.command.help.HelpSupport;
import io.gitlab.lone64.framework.bukkit.api.util.number.NumberUtil;
import org.bukkit.entity.Player;

import java.util.List;

import static io.gitlab.lone64.framework.bukkit.api.util.string.ColorUtil.format;

public class HelloCommand {
    public static void register(CommandRoot root) {
        root.register(new BaseCommand(
                "테스트",
                "테스트 명령어입니다.",
                Condition.PLAYER
        ) {
            @Override
            protected boolean onCommand(Player player, CommandInput input) {
                if (input.empty()) {
                    return HelpSupport.auto(this, player, 1, i -> player.sendMessage(format("&c페이지를 찾을 수 없습니다.")));
                } else if (NumberUtil.getIntegerOrNull(input.string(0)) != null) {
                    return HelpSupport.auto(this, player, input.integer(0), i -> player.sendMessage(format("&c페이지를 찾을 수 없습니다.")));
                }
                player.sendMessage(format("&c존재하지 않는 명령어입니다."));
                return true;
            }
        }.then(new BaseCommand(
                "닉네임",
                "플레이어의 닉네임을 출력합니다.",
                Condition.PLAYER
        ) {
            @Override
            protected boolean onCommand(Player player, CommandInput input) {
                player.sendMessage(format("&a당신의 닉네임 : %s".formatted(player.getName())));
                return true;
            }
        }).then(new BaseCommand(
                "입력",
                "특정 값을 입력하고 출력합니다.",
                Condition.PLAYER
        ) {
            @Override
            protected boolean onCommand(Player player, CommandInput input) {
                new TextFieldBuilder(root.getPlugin(), player)
                        .delay(true)
                        .title("&6&l테스트 플러그인")
                        .subtitle("&e출력하실 값을 채팅에 입력해주세요.")
                        .onInit(p -> p.sendMessage(format("&a채팅에 출력하실 값을 입력해주세요!")))
                        .onInput((p, value) -> {
                            p.sendMessage("입력값 : %s".formatted(value));
                            return true;
                        })
                        .onCancel(p -> p.sendMessage(format("&c입력이 취소되었습니다.")))
                        .create();
                return true;
            }
        }));
    }
}
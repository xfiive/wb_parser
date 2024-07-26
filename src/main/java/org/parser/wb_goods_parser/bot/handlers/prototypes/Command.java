package org.parser.wb_goods_parser.bot.handlers.prototypes;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public enum Command {
    START(List.of("/start", "Запустить бота")),
    HELP(List.of("/help", "О боте")),
    EDIT_QUERY(List.of("Изменить поисковой запрос")),
    VIEW_PRODUCTS(List.of("Смотреть продукты")),
    NEXT(List.of("Далее")),
    UNDEFINED(List.of("")),
    MENU(List.of("Меню"));

    private final List<String> commands;

    Command(List<String> commands) {
        this.commands = commands;
    }

    public static @Nullable Command fromString(String command) {
        for (Command c : Command.values()) {
            if (c.getCommands().contains(command)) {
                return c;
            }
        }
        return null;
    }


    public List<String> getCommands() {
        return commands;
    }
}

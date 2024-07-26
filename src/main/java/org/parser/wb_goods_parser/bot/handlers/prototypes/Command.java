package org.parser.wb_goods_parser.bot.handlers.prototypes;

import org.jetbrains.annotations.Nullable;

public enum Command {
    START("/start"),
    HELP("/help"),
    EDIT_QUERY("Изменить поисковой запрос"),
    VIEW_PRODUCTS("Смотреть продукты"),
    NEXT("Далее"),
    MENU("Меню");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static @Nullable Command fromString(String command) {
        for (Command c : Command.values()) {
            if (c.getCommand().equalsIgnoreCase(command)) {
                return c;
            }
        }
        return null;
    }

    public String getCommand() {
        return command;
    }
}

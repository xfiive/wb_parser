package org.parser.wb_goods_parser.bot.handlers.prototypes;

import org.jetbrains.annotations.Nullable;

public enum Command {
    START("/start"),
    HELP("/help"),
    EDIT_QUERY("edit_query"),
    VIEW_PRODUCTS("view_products"),
    NEXT("next"),
    MENU("menu");

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

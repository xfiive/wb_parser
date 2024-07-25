package org.parser.wb_goods_parser.bot.handlers.prototypes;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void handle(@NotNull Update update, TelegramLongPollingBot bot);
}

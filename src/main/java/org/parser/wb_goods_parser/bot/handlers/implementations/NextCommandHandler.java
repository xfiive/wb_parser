package org.parser.wb_goods_parser.bot.handlers.implementations;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NextCommandHandler implements CommandHandler {

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
    }

}

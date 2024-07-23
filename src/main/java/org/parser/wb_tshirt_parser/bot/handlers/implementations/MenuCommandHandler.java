package org.parser.wb_tshirt_parser.bot.handlers.implementations;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_tshirt_parser.bot.handlers.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MenuCommandHandler implements CommandHandler {

    @Override
    public void handle(@NotNull Update update, TelegramLongPollingBot bot) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        new StartCommandHandler().handle(update, bot);
    }
}

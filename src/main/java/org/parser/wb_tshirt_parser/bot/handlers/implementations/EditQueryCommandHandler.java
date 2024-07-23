package org.parser.wb_tshirt_parser.bot.handlers.implementations;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_tshirt_parser.bot.handlers.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EditQueryCommandHandler implements CommandHandler {

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage(bot, chatId, "Введите новый поисковой запрос:", null);
    }


}

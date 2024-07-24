package org.parser.wb_tshirt_parser.bot.handlers.implementations;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_tshirt_parser.bot.handlers.CommandHandler;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class StartCommandHandler implements CommandHandler {

    private @NotNull ReplyKeyboardMarkup createMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();



        return markup;
    }

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
        long chatId = update.getMessage().getChatId();


        sendMessage(bot, chatId, "Выберите действие:", this.createMarkup());
    }

}

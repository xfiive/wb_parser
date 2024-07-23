package org.parser.wb_tshirt_parser.bot.handlers.implementations;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_tshirt_parser.bot.handlers.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class StartCommandHandler implements CommandHandler {

    private @NotNull InlineKeyboardMarkup createMarkup() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row = new ArrayList<>();

        row.add(InlineKeyboardButton.builder().text("Изменить запрос").callbackData("edit_query").build());
        row.add(InlineKeyboardButton.builder().text("Смотреть товары").callbackData("view_products").build());

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        markup.setKeyboard(rows);

        return markup;
    }

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
        long chatId = update.getMessage().getChatId();

        sendMessage(bot, chatId, "Выберите действие:", this.createMarkup());
    }

}

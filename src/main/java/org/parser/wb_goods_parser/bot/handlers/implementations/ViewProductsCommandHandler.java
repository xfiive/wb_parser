package org.parser.wb_goods_parser.bot.handlers.implementations;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ViewProductsCommandHandler implements CommandHandler {

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
//        long chatId = update.getCallbackQuery().getMessage().getChatId();
//
//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        row.add(InlineKeyboardButton.builder().text("Далее").callbackData("next").build());
//        row.add(InlineKeyboardButton.builder().text("В меню").callbackData("menu").build());
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//        rows.add(row);
//        markup.setKeyboard(rows);

    }

}

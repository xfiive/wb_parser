package org.parser.wb_goods_parser.bot.message_sender;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.entities.TelegramMessageParameters;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface MessageSender<T> {
    void sendMessage(T params);

    TelegramMessageParameters formMessageParameters(@NotNull Update update, TelegramLongPollingBot bot, ReplyKeyboardMarkup markup, String messageText);

    ReplyKeyboardMarkup formReplyMarkup(boolean isPersistent, @NotNull List<String> buttons, boolean isBotActivated);

}

package org.parser.wb_goods_parser.entities;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Data
public class TelegramMessageParameters {
    @NotNull
    private TelegramLongPollingBot bot;
    private long chatId;
    private String text;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
}

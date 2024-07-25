package org.parser.wb_goods_parser.bot.handlers.implementations;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommandHandler implements CommandHandler {

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
//        long chatId = update.getMessage().getChatId();
//
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Добро пожаловать в бота");
//        var markup = this.createMarkup();
//        message.setReplyMarkup(markup);
//
//        try {
//            bot.execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

//        sendMessage(bot, chatId, "Выберите действие:", this.createMarkup());
    }

}

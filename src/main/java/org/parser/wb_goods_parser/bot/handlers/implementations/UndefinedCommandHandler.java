package org.parser.wb_goods_parser.bot.handlers.implementations;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.parser.wb_goods_parser.bot.message_sender.MessageSender;
import org.parser.wb_goods_parser.entities.TelegramMessageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UndefinedCommandHandler implements CommandHandler {

    private final MessageSender<TelegramMessageParameters> messageSender;

    @Override
    public void handle(@NotNull Update update, TelegramLongPollingBot bot) {
        String messageText = "О нет, я не знаю такой команды(\n\nВыбери из тех, которые я предлагаю тебе!";
        var markup = this.messageSender.formReplyMarkup(true, List.of("Изменить поисковой запрос", "Смотреть продукты"), true);
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }
}

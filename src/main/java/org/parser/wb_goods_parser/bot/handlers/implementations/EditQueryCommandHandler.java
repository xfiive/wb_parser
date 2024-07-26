package org.parser.wb_goods_parser.bot.handlers.implementations;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.parser.wb_goods_parser.bot.message_sender.MessageSender;
import org.parser.wb_goods_parser.entities.ChatData;
import org.parser.wb_goods_parser.entities.TelegramMessageParameters;
import org.parser.wb_goods_parser.services.ChatDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EditQueryCommandHandler implements CommandHandler {

    private final ChatDataService chatDataService;
    private final MessageSender<TelegramMessageParameters> messageSender;

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
        Mono<ChatData> existingChatData = this.chatDataService.findChatById(update.getMessage().getChatId());

        existingChatData.hasElement().subscribe(chatData -> {
            if (chatData)
                handleExistingChat(update, bot);
            else
                handleNotExistingChat(update, bot);
        });
    }


    private void handleExistingChat(@NotNull Update update, TelegramLongPollingBot bot) {
        String messageText = "Введи название и описание того, что ты хочешь искать.";
        var markup = this.messageSender.formReplyMarkup(true, List.of("Вернуться в меню"), true);
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }

    private void handleNotExistingChat(@NotNull Update update, TelegramLongPollingBot bot) {
        String messageText = "Ух ты, даже не знаю, как так вышло, но мы с тобой всё ещё не знакомы( \n\nЗапусти меня и давай начнём работать вместе! \uD83D\uDE0A";
        var markup = this.messageSender.formReplyMarkup(true, List.of("О боте", "Запустить бота"), true);
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }


}

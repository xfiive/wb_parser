package org.parser.wb_goods_parser.bot.handlers.implementations;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.parser.wb_goods_parser.bot.message_sender.MessageSender;
import org.parser.wb_goods_parser.entities.ChatData;
import org.parser.wb_goods_parser.entities.ChatState;
import org.parser.wb_goods_parser.entities.TelegramMessageParameters;
import org.parser.wb_goods_parser.services.ChatDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StartCommandHandler implements CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(StartCommandHandler.class);
    private final MessageSender<TelegramMessageParameters> messageSender;
    private final ChatDataService chatDataService;

    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
        Mono<ChatData> existingChat = this.chatDataService.findChatById(update.getMessage().getChatId());
        existingChat.hasElement().subscribe(chatData -> {
            if (chatData)
                this.handleExistingChat(update, bot);
            else
                this.handleNotExistingChat(update, bot);
        });

    }

    private void handleExistingChat(@NotNull Update update, TelegramLongPollingBot bot) {
        String messageText = "Кажется, мы с тобой уже знакомы) \uD83D\uDE0A \n\nЧтобы выбрать то, что хочешь искать, нажми на кнопку 'Изменить поисковой запрос', затем введи свой запрос и тебе будет предложено изменить запрос ещё раз или же просмотреть найденные товары.";
        var markup = this.messageSender.formReplyMarkup(true, List.of("Изменить поисковой запрос", "Смотреть продукты"), true);
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }

    private void handleNotExistingChat(@NotNull Update update, TelegramLongPollingBot bot) {
        saveNewChat(update);

        String messageText = "Отлично, теперь мы можем начать! \uD83D\uDE0A \n\nЧтобы выбрать то, что хочешь искать, нажми на кнопку 'Изменить поисковой запрос', затем введи свой запрос и тебе будет предложено изменить запрос ещё раз или же просмотреть найденные товары.";
        var markup = this.messageSender.formReplyMarkup(true, List.of("Изменить поисковой запрос", "Смотреть продукты"), true);
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }


    private void saveNewChat(@NotNull Update update) {
        ChatData chatData = new ChatData();
        chatData.setChatId(update.getMessage().getChatId());
        chatData.setCurrentQuery("");
        chatData.setBotStarted(true);
        chatData.setState(ChatState.NONE);
        this.chatDataService.addNewChat(chatData).hasElement().subscribe(savedChat -> {
            if (savedChat) {
                logger.info("Chat saved successfully");
            } else {
                logger.error("Failed to save chat");
            }
        });
    }


}

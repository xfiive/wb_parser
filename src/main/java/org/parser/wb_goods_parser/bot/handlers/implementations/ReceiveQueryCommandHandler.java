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
public class ReceiveQueryCommandHandler implements CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(ReceiveQueryCommandHandler.class);

    private final ChatDataService chatDataService;
    private final MessageSender<TelegramMessageParameters> messageSender;

    @Override
    public void handle(@NotNull Update update, TelegramLongPollingBot bot) {
        updateChatData(update);

        String messageText = "Отлично, я записал твой запрос.\n\n";
        var markup = this.messageSender.formReplyMarkup(true, List.of("Изменить поисковой запрос", "Смотреть продукты"), true);
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }

    private void updateChatData(@NotNull Update update) {
        Mono<ChatData> existingChatData = this.chatDataService.findChatById(update.getMessage().getChatId());
        existingChatData.subscribe(chatData -> {
            chatData.setState(ChatState.NONE);
            chatData.setCurrentQuery(update.getMessage().getText());
            this.chatDataService.updateChat(chatData.getChatId(), chatData).hasElement().subscribe(updatedChat -> {
                if (!updatedChat) {
                    log.error("Failed to update chat state");
                }
            });
        });
    }

}

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
public class HelpCommandHandler implements CommandHandler {

    private final MessageSender<TelegramMessageParameters> messageSender;
    private final ChatDataService chatDataService;

    @Override
    public void handle(@NotNull Update update, TelegramLongPollingBot bot) {
        Mono<ChatData> existingChat = this.chatDataService.findChatById(update.getMessage().getChatId());
        existingChat.hasElement().subscribe(chatData -> {
            if (chatData)
                this.handleExistingChat(update, bot);
            else
                this.handleNotExistingChat(update, bot);
        });
    }

    private void handleExistingChat(@NotNull Update update, TelegramLongPollingBot bot) {
        String messageText = "Приветствую ещё раз! \uD83D\uDE0A \n\nЯ - Бот, который был создан с целью немного упростить жизнь людям.\nТы можешь сказать мне, что ты хочешь искать, а дальше я буду показывать тебе товары, которые я найду по данной теме.\n\nА теперь давай продолжим наш с тобой поиск и просмотр товаров) ";
        var markup = this.messageSender.formReplyMarkup(true, List.of("Изменить поисковой запрос", "Смотреть продукты"), true);
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }

    private void handleNotExistingChat(@NotNull Update update, TelegramLongPollingBot bot) {
        var markup = this.messageSender.formReplyMarkup(true, List.of("О боте", "Запустить бота"), false);
        String messageText = "Приветствую! \uD83D\uDE0A \n\nЯ - Бот, который был создан с целью немного упростить жизнь людям.\nТы можешь сказать мне, что ты хочешь искать, а дальше я буду показывать тебе товары, которые я найду по данной теме.\n\nНапиши '/start' или нажми на кнопку, чтобы запустить меня!";
        var messageParams = this.messageSender.formMessageParameters(update, bot, markup, messageText);

        this.messageSender.sendMessage(messageParams);
    }
}

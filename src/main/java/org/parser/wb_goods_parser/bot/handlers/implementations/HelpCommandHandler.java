package org.parser.wb_goods_parser.bot.handlers.implementations;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.parser.wb_goods_parser.bot.message_sender.MessageSender;
import org.parser.wb_goods_parser.entities.ChatData;
import org.parser.wb_goods_parser.entities.TelegramMessageParameters;
import org.parser.wb_goods_parser.services.ChatDataService;
import org.parser.wb_goods_parser.services.MessageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HelpCommandHandler implements CommandHandler {

    private final MessageSender<TelegramMessageParameters> messageSender;
    private final ChatDataService chatDataService;
    private final MessageDataService messageDataService;

    @Override
    public void handle(@NotNull Update update, TelegramLongPollingBot bot) {
        var chatId = update.getCallbackQuery().getMessage().getChatId();

        Mono<ChatData> existingChat = this.chatDataService.findChatById(update.getCallbackQuery().getMessage().getChatId());
        existingChat.hasElement().subscribe(chatData -> {
            if (chatData)
                this.handleExistingChat(update, bot);
            else
                this.handleNotExistingChat(update, bot);
        });
    }

    private void handleExistingChat(Update update, TelegramLongPollingBot bot) {

    }

    private void handleNotExistingChat(Update update, TelegramLongPollingBot bot) {

    }


    private ReplyKeyboardMarkup formReplyMarkup(boolean isPersistent, List<String> buttons) {
//        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
//        markup.setIsPersistent(true);
//
//        KeyboardButton buttonQuery = new KeyboardButton("Редактировать поисковой запрос");
//        KeyboardButton buttonFeed = new KeyboardButton("Смотреть товары");
//
//        KeyboardRow buttonLayerFirst = new KeyboardRow();
//        buttonLayerFirst.add(buttonFeed);
//        buttonLayerFirst.add(buttonQuery);
//
//        List<KeyboardRow> buttons = new ArrayList<>();
//
//        buttons.add(buttonLayerFirst);
//
//        markup.setKeyboard(buttons);
        return null;
    }

}

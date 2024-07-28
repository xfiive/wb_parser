package org.parser.wb_goods_parser.bot.handlers.implementations;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.parser.wb_goods_parser.parser.PageConsumer;
import org.parser.wb_goods_parser.services.ChatDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ViewProductsCommandHandler implements CommandHandler {

    private final ChatDataService chatDataService;
    private final PageConsumer pageConsumer;


    @Override
    public void handle(@NotNull Update update, @NotNull TelegramLongPollingBot bot) {
        var currentQuery = Objects.requireNonNull(this.chatDataService.findChatById(update.getMessage().getChatId())
                .block()).getCurrentQuery();

        this.pageConsumer.consumePage(currentQuery);
    }

}

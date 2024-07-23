package org.parser.wb_tshirt_parser.bot;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_tshirt_parser.bot.handlers.Command;
import org.parser.wb_tshirt_parser.bot.handlers.CommandHandler;
import org.parser.wb_tshirt_parser.bot.handlers.implementations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    private final Map<Command, CommandHandler> commandHandlers = new HashMap<>();
//    private final Map<Long, List<Integer>> userMessageHistory = new HashMap<>();


    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    public MyTelegramBot() {
        commandHandlers.put(Command.START, new StartCommandHandler());
        commandHandlers.put(Command.EDIT_QUERY, new EditQueryCommandHandler());
        commandHandlers.put(Command.VIEW_PRODUCTS, new ViewProductsCommandHandler());
        commandHandlers.put(Command.NEXT, new NextCommandHandler());
        commandHandlers.put(Command.MENU, new MenuCommandHandler());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Command command = Command.fromString(messageText);

            if (command != null) {
                CommandHandler handler = commandHandlers.get(command);
                if (handler != null) {
                    handler.handle(update, this);
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            Command command = Command.fromString(callbackData);

            if (command != null) {
                CommandHandler handler = commandHandlers.get(command);
                if (handler != null) {
                    handler.handle(update, this);
                }
            }
        }
    }

//    public void storeMessage(long chatId, int messageId) {
//        userMessageHistory.computeIfAbsent(chatId, k -> new ArrayList<>()).add(messageId);
//    }
//
//    public void removePreviousKeyboards(long chatId) {
//        List<Integer> messageIds = userMessageHistory.getOrDefault(chatId, new ArrayList<>());
//
//        for (int messageId : messageIds) {
//            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
//            editMarkup.setChatId(String.valueOf(chatId));
//            editMarkup.setMessageId(messageId);
//            editMarkup.setReplyMarkup(null);
//
//            try {
//                execute(editMarkup);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // Clear the history after removing keyboards
//        userMessageHistory.put(chatId, new ArrayList<>());
//    }

}


package org.parser.wb_tshirt_parser.bot;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_tshirt_parser.bot.handlers.Command;
import org.parser.wb_tshirt_parser.bot.handlers.CommandHandler;
import org.parser.wb_tshirt_parser.bot.handlers.implementations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(MyTelegramBot.class);

    private final Map<Command, CommandHandler> commandHandlers = new HashMap<>();

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
            logger.info("Received a message with text: {}", update.getMessage().getText());
        }

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

    @PostConstruct
    public void init() {
        initializeCommands();
    }

    public void initializeCommands() {
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/start", "Запустить бота"));
        commandList.add(new BotCommand("/help", "Информация о боте"));

        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commandList);
        try {
            execute(setMyCommands);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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


package org.parser.wb_goods_parser.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.bot.handlers.implementations.*;
import org.parser.wb_goods_parser.bot.handlers.prototypes.Command;
import org.parser.wb_goods_parser.bot.handlers.prototypes.CommandHandler;
import org.parser.wb_goods_parser.services.ChatDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.parser.wb_goods_parser.bot.handlers.prototypes.Command.UNDEFINED;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MyTelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(MyTelegramBot.class);

    private final Map<Command, CommandHandler> commandHandlers = new HashMap<>();

    private final StartCommandHandler startCommandHandler;
    private final HelpCommandHandler helpCommandHandler;
    private final EditQueryCommandHandler editQueryCommandHandler;
    private final ViewProductsCommandHandler viewProductsCommandHandler;
    private final NextCommandHandler nextCommandHandler;
    private final MenuCommandHandler menuCommandHandler;
    private final ReceiveQueryCommandHandler receiveQueryCommandHandler;
    private final ChatDataService chatDataService;

    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    @PostConstruct
    public void init() {
        commandHandlers.put(Command.START, startCommandHandler);
        commandHandlers.put(Command.HELP, helpCommandHandler);
        commandHandlers.put(Command.EDIT_QUERY, editQueryCommandHandler);
        commandHandlers.put(Command.VIEW_PRODUCTS, viewProductsCommandHandler);
        commandHandlers.put(Command.NEXT, nextCommandHandler);
        commandHandlers.put(UNDEFINED, receiveQueryCommandHandler);
        commandHandlers.put(Command.MENU, menuCommandHandler);
        initializeCommands();
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

        if (update.hasCallbackQuery()) {
            logger.info("Received a message with callback query: {}", update.getCallbackQuery());
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Command command = Command.fromString(messageText);

            if (command != null) {
                CommandHandler handler = commandHandlers.get(command);
                if (handler != null) {
                    logger.info("Handler class handling an event: {}", handler.getClass());
                    handler.handle(update, this);
                }
            } else {
                CommandHandler handler = commandHandlers.get(UNDEFINED);
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
}

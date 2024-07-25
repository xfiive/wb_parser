package org.parser.wb_goods_parser.bot.message_sender;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.parser.wb_goods_parser.entities.TelegramMessageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramMessageSender implements MessageSender<TelegramMessageParameters> {

    private static final Logger logger = LoggerFactory.getLogger(TelegramMessageSender.class);

    @Override
    public void sendMessage(@NotNull TelegramMessageParameters params) {
        SendMessage message = new SendMessage();
        message.setText(params.getText());
        message.setChatId(params.getChatId());
        if (params.getReplyKeyboardMarkup() != null)
            message.setReplyMarkup(params.getReplyKeyboardMarkup());
        this.send(message, params.getBot());
    }

    public @NotNull TelegramMessageParameters formMessageParameters(@NotNull Update update, TelegramLongPollingBot bot, ReplyKeyboardMarkup markup, String messageText) {
        var messageParams = new TelegramMessageParameters(bot);
        messageParams.setChatId(update.getCallbackQuery().getMessage().getChatId());
        messageParams.setReplyKeyboardMarkup(markup);
        messageParams.setText(messageText);
        return messageParams;
    }


    public @Nullable ReplyKeyboardMarkup formReplyMarkup(boolean isPersistent, @NotNull List<String> buttons, boolean isBotActivated) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setIsPersistent(isPersistent);

        List<KeyboardRow> keyboardRows = buttons.stream()
                .map(buttonText -> {
                    KeyboardButton button = new KeyboardButton(buttonText);
                    KeyboardRow row = new KeyboardRow();
                    row.add(button);
                    return row;
                })
                .toList();

        markup.setKeyboard(keyboardRows);

        return null;
    }

    private void send(SendMessage message, @NotNull TelegramLongPollingBot bot) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            logger.error("Failed to send a bot message: {}", e.getMessage());
        }
    }
}

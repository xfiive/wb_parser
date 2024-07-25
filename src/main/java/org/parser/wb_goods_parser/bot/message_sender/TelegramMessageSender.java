package org.parser.wb_goods_parser.bot.message_sender;

import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.entities.TelegramMessageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    private void send(SendMessage message, @NotNull TelegramLongPollingBot bot) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            logger.error("Failed to send a bot message: {}", e.getMessage());
        }
    }
}

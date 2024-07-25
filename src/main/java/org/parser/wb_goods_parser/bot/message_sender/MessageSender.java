package org.parser.wb_goods_parser.bot.message_sender;

public interface MessageSender<T> {
    void sendMessage(T params);
}

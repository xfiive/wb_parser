package org.parser.wb_goods_parser.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.entities.MessageData;
import org.parser.wb_goods_parser.repositories.MessageDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessageDataService {

    private final MessageDateRepository messageDateRepository;

    public Mono<MessageData> findMessageById(long id) {
        return this.messageDateRepository.findById(id);
    }

    public Flux<MessageData> findAllByChatId(long id) {
        return this.messageDateRepository.findAllByChatId(id);
    }

    public Mono<MessageData> addNewMessage(@NotNull MessageData message) {
        return this.findMessageById(message.getMessageId())
                .flatMap(existingMessage -> Mono.<MessageData>empty())
                .switchIfEmpty(this.messageDateRepository.save(message));
    }

    public Mono<MessageData> updateMessage(long id, MessageData message) {
        return this.messageDateRepository.findById(id).flatMap(existingMessage -> {
            existingMessage.setChatId(message.getChatId());
            existingMessage.setMessageId(id);
            return this.messageDateRepository.save(existingMessage);
        }).switchIfEmpty(Mono.empty());
    }

}

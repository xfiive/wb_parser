package org.parser.wb_goods_parser.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.entities.ChatData;
import org.parser.wb_goods_parser.repositories.ChatDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChatDataService {

    private final ChatDataRepository chatDataRepository;

    public Mono<ChatData> findChatById(long id) {
        return this.chatDataRepository.findById(id);
    }

    private @NotNull Mono<Boolean> checkIfChatExists(long id) {
        return this.findChatById(id).hasElement();
    }

    public Mono<ChatData> addNewChat(@NotNull ChatData chat) {
        return this.findChatById(chat.getChatId())
                .flatMap(existingChat -> Mono.<ChatData>empty())
                .switchIfEmpty(this.chatDataRepository.save(chat));
    }

    public Mono<ChatData> updateChat(long id, ChatData chat) {
        return this.chatDataRepository.findById(id).flatMap(existingChat -> {
            existingChat.setChatId(id);
            existingChat.setBotStarted(chat.isBotStarted());
            existingChat.setCurrentQuery(chat.getCurrentQuery());
            return this.chatDataRepository.save(existingChat);
        }).switchIfEmpty(Mono.empty());
    }


}

package org.parser.wb_goods_parser.services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.parser.wb_goods_parser.entities.ChatData;
import org.parser.wb_goods_parser.repositories.ChatDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChatDataService {

    private final ChatDataRepository chatDataRepository;

    public Mono<ChatData> findChatById(long id) {
        return this.chatDataRepository.findById(id).flatMap(chatData -> {
            try {
                String decompressedQuery = CompressionService.decompress(chatData.getCurrentQuery());
                chatData.setCurrentQuery(decompressedQuery);
                return Mono.just(chatData);
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
    }

    public Mono<ChatData> addNewChat(@NotNull ChatData chat) {
        return this.findChatById(chat.getChatId())
                .flatMap(existingChat -> Mono.<ChatData>empty())
                .switchIfEmpty(Mono.defer(() -> {
                    try {
                        String compressedQuery = CompressionService.compress(chat.getCurrentQuery());
                        chat.setCurrentQuery(compressedQuery);
                        return this.chatDataRepository.save(chat);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                }));
    }

    public Mono<ChatData> updateChat(long id, ChatData chat) {
        return this.chatDataRepository.findById(id).flatMap(existingChat -> {
            try {
                existingChat.setChatId(id);
                existingChat.setCurrentQuery(CompressionService.compress(chat.getCurrentQuery()));
                existingChat.setState(chat.getState());
                return this.chatDataRepository.save(existingChat);
            } catch (Exception e) {
                return Mono.error(e);
            }
        }).switchIfEmpty(Mono.empty());
    }
}

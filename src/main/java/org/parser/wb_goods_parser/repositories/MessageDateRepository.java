package org.parser.wb_goods_parser.repositories;

import org.parser.wb_goods_parser.entities.MessageData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MessageDateRepository extends ReactiveMongoRepository<MessageData, Long> {
    Flux<MessageData> findAllByChatId(long id);
}

package org.parser.wb_goods_parser.repositories;

import org.parser.wb_goods_parser.entities.ChatData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDataRepository extends ReactiveMongoRepository<ChatData, Long> {
}

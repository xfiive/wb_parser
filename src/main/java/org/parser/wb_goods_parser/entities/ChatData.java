package org.parser.wb_goods_parser.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Data
@Document(collection = "chat_data")
public class ChatData {
    @Id
    private Long chatId;

    @Field("cq")
    private String currentQuery;

    @Field("pg")
    private int currentPage;

    @Field("idx")
    private int currentIndex;

    @Field("shownHashes")
    private Set<String> shownHashes;

    @Field("st")
    private ChatState state;
}

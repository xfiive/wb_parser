package org.parser.wb_goods_parser.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "chat_data")
public class ChatData {

    @Id
    private Long chatId;

    @Field
    private String currentQuery;

    @Field
    private boolean isBotStarted;

    @Field
    private ChatState state;
}

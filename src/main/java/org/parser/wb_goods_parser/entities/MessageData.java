package org.parser.wb_goods_parser.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "message_data")
public class MessageData {
    @Id
    private Long chatId;

    @Field
    private Long messageId;
}

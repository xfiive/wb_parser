package org.parser.wb_goods_parser.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class MessageData {

    @Id
    private Long chatId;

    @Field
    private Long messageId;
}

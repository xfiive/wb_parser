package org.parser.wb_tshirt_parser.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class MessageData {

    @Id
    private Long chatId;

    @Field
    private Long messageId;

    @Field
    private boolean isActive; // represents if message has it's links still active
}

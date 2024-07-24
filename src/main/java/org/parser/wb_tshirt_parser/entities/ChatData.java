package org.parser.wb_tshirt_parser.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ChatData {

    @Id
    private Long chatId;

    @Field
    private String currentQuery;
}

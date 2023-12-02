package com.zinoviev.entity.dto.update.include;

import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.entity.enums.MessageType;
import lombok.Data;
import java.util.List;

@Data
public class MessageDto {

    //user
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;

    private Integer messageId;
    private Long chatId;
    private String text;

    private String callbackData;
    private Integer callbackMessageId;

    private MessageType messageType;

    private KeyboardType keyboardType;
    private List<List<InlineButtonDto>> buttons;
}

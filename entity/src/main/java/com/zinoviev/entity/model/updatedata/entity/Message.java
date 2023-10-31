package com.zinoviev.entity.model.updatedata.entity;

import lombok.Data;

import java.util.List;

@Data
public class Message {

    //user
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;

    //chat
    private Integer messageId;//TODO не одинаковые ли они
    private Long chatId;
    private String text;

    private String callbackData;
    private Integer callbackMessageId; //TODO не одинаковые ли они

    private List<List<InlineButton>> buttons;
}

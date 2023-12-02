package com.zinoviev.entity.dto.data.quest;

import lombok.Data;


@Data
public class QuestInfoMessageDto {

    private long id;

    private String message;

    private byte[] image;

    private long questNodeDtoMessages;

    private long arrivedMessages;

    private long outerMessages;

    private long externalMessages;
}


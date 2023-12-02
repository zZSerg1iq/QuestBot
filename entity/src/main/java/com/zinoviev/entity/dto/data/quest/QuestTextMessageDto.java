package com.zinoviev.entity.dto.data.quest;

import lombok.Data;

@Data
public class QuestTextMessageDto {

    private long id;

    private String message;

    private long expectedAnswers;

    private long correctMessages;

    private long incorrectMessages;

}

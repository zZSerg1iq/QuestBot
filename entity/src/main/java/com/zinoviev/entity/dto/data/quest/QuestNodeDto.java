package com.zinoviev.entity.dto.data.quest;

import lombok.Data;

import java.util.List;

@Data
public class QuestNodeDto {

    private long id;

    private String stageName;

    private long questId;

    private long nextNode;

    private List<QuestInfoMessageDto> questMessages;

    private List<QuestTextMessageDto> expectedUserAnswers;

    private int requiredAnswersCount;

    private boolean reactOnCorrectAnswer;

    private List<QuestTextMessageDto> correctAnswersReactMessages;

    private boolean reactOnWrongAnswer;

    private List<QuestTextMessageDto> wrongAnswersReactMessages;

    private GeoPointDto geoPoint;

    private boolean switchToNextNodeWhenPointReached;

    private int pauseBeforeSwitch;

    @Override
    public String toString() {
        return "QuestNodeDto{" +
                "id=" + id +
                ", stageName='" + stageName + '\'' +
                ", questId=" + questId +
                ", nextNode=" + nextNode +
                ", questMessages=" + (questMessages != null? questMessages.size(): "null ") +
                ", expectedUserAnswers=" + (expectedUserAnswers != null? expectedUserAnswers.size(): "null") +
                ", requiredAnswersCount=" + requiredAnswersCount +
                ", reactOnCorrectAnswer=" + reactOnCorrectAnswer +
                ", correctAnswersReactMessages=" + (correctAnswersReactMessages != null? correctAnswersReactMessages.size() : "null") +
                ", reactOnWrongAnswer=" + reactOnWrongAnswer +
                ", wrongAnswersReactMessages=" + (wrongAnswersReactMessages != null? wrongAnswersReactMessages.size() : "null") +
                ", geoPoint=" + geoPoint +
                ", switchToNextNodeWhenPointReached=" + switchToNextNodeWhenPointReached +
                ", pauseBeforeSwitch=" + pauseBeforeSwitch +
                '}';
    }
}

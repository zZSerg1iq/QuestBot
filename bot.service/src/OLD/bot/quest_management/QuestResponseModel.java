package com.zinoviev.questbot.OLD.bot.quest_management;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class QuestResponseModel {

    private LinkedHashMap<String, Integer> textMessageResponse;
    private LinkedHashMap<String, Integer> geoPositionResponse;
    private String geoPositionDistanceMessage;

    private boolean theLastNode = false;

    @Override
    public String toString() {
        return "QuestResponseModel{" +
                "textMessageResponse=" + textMessageResponse +
                ", geoPositionResponse=" + geoPositionResponse +
                ", geoPositionDistanceMessage='" + geoPositionDistanceMessage + '\'' +
                ", theLastNode=" + theLastNode +
                '}';
    }
}

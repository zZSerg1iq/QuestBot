package com.zinoviev.questbot.OLD.bot.quest_management;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class MemberGeoPointStatus {

    private LinkedHashMap<String, Integer> radiusMessages;
    private String distanceMessage = null;


    @Override
    public String toString() {
        return "MemberGeoPointStatus{" +
                "radiusMessages=" + radiusMessages +
                ", distanceMessage='" + distanceMessage + '\'' +
                '}';
    }
}

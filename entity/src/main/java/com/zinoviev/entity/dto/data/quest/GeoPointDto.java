package com.zinoviev.entity.dto.data.quest;

import lombok.Data;

import java.util.List;

@Data
public class GeoPointDto {

    private long id;

    private double longitude;

    private double latitude;

    private int selfRadius;

    private List<QuestInfoMessageDto> arrivedMessages;

    private int geoPointOuterRadius;

    private List<QuestInfoMessageDto> outerMessages;

    private int geoPointExternalRadius;

    private List<QuestInfoMessageDto> externalMessages;

    private long questNode;

    @Override
    public String toString() {
        return "GeoPointDto{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", selfRadius=" + selfRadius +
                ", arrivedMessages=" + arrivedMessages.size() +
                ", geoPointOuterRadius=" + geoPointOuterRadius +
                ", outerMessages=" + outerMessages.size() +
                ", geoPointExternalRadius=" + geoPointExternalRadius +
                ", externalMessages=" + externalMessages.size() +
                ", geoPoint=" + questNode +
                '}';
    }
}

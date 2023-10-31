package com.zinoviev.questbot.OLD.bot.entity.models.quest;

import com.zinoviev.sandbox.data.entity.quest.GeoPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BotGeoPoint {

    /***
     * Так как фактические координаты достигнуть достаточно сложно, потому что необходимо
     * встать буквально на нужный квадратный метр земли, квестовая геоточка
     * фактически представляет собой три окружности разного диаметра описанные вокуг физической геоточки.
     * Внутренняя по сути является "увеличенной геоточкой", вход в которую будет засчитан как достижение цели
     * Остальные используются для того, что бы дать понять игроку что он на верном пути.
     * Вход в каждую из геоточек может сопровождаться выводом какого-либо сообщения.
     * Так же можно полностью отказаться от использования окружностей или сделать какие-либо из них одного размера
     * Но это может повлечь сложности в орентировании для самих игроков.
     ***/



    private long id;

    private long nodeId;

    private double longitude;
    private double latitude;

    //Внутренний радиус. Вход в который будет засчитано как достижение геоточки.
    private int geoPointRadius;
    //Сообщения, при входе в радиус
    private LinkedHashMap<String, Integer> onPointMessages;

    //Средний радиус
    private int geoPointMeddleRadius = -1;
    //Сообщения, при входе в радиус
    private LinkedHashMap<String, Integer> middleRadiusReachedMessages;

    //Внешний радиус
    private int geoPointOuterRadius = -1;
    //Сообщения, при входе в радиус
    private LinkedHashMap<String, Integer> outerRadiusReachedMessages;


    public BotGeoPoint(GeoPoint geoPoint) {
        id = geoPoint.getId();
        nodeId = geoPoint.getNodeId().getId();
        longitude = geoPoint.getLongitude();
        latitude = geoPoint.getLatitude();


        geoPointRadius = geoPoint.getGeoPointRadius();
        if (geoPoint.getOnPointMessages() != null) {
            List<String> mess = Arrays.stream(geoPoint.getOnPointMessages().split("\n")).toList();
            onPointMessages = new LinkedHashMap<>();
            for (String s: mess ) {
                int x = Integer.parseInt( s.substring(s.lastIndexOf(" ")+1) );
                onPointMessages.put(s.substring(0, s.lastIndexOf(" ")), x);
            }
        }


        geoPointMeddleRadius = geoPoint.getGeoPointMeddleRadius();
        if (geoPoint.getGeoPointMeddleRadiusReachedMessages() != null) {
            List<String> mess = Arrays.stream(geoPoint.getGeoPointMeddleRadiusReachedMessages().split("\n")).toList();
            middleRadiusReachedMessages = new LinkedHashMap<>();
            for (String s: mess ) {
                int x = Integer.parseInt( s.substring(s.lastIndexOf(" ")+1) );
                middleRadiusReachedMessages.put(s.substring(0, s.lastIndexOf(" ")), x);
            }
        }


        geoPointOuterRadius = geoPoint.getGeoPointOuterRadius();
        if (geoPoint.getGeoPointOuterRadiusReachedMessages() != null) {
            List<String> mess = Arrays.stream(geoPoint.getGeoPointOuterRadiusReachedMessages().split("\n")).toList();
            outerRadiusReachedMessages = new LinkedHashMap<>();
            for (String s: mess ) {
                int x = Integer.parseInt( s.substring(s.lastIndexOf(" ")+1) );
                outerRadiusReachedMessages.put(s.substring(0, s.lastIndexOf(" ")), x);
            }
        }
    }

    @Override
    public String toString() {
        return  "\n----------------------GEO-----------------------" +
                "\n    nodeId=" + nodeId +
                ",\n\n longitude=" + longitude +
                ",\n\n latitude=" + latitude +
                ",\n\n geoPointRadius=" + geoPointRadius +
                ",\n\n onPointMessages=" + onPointMessages +
                ",\n\n geoPointMeddleRadius=" + geoPointMeddleRadius +
                ",\n\n geoPointMeddleRadiusReachedMessages=" + middleRadiusReachedMessages +
                ",\n\n geoPointOuterRadius=" + geoPointOuterRadius +
                ",\n\n geoPointOuterRadiusReachedMessages=" + outerRadiusReachedMessages+
                "\n---------------------------------------------\n\n";
    }
}

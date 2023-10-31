package com.zinoviev.questbot.OLD.data.entity.quest;

import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class GeoPoint {

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


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    private QuestNode nodeId;

    @Column
    private double longitude;
    @Column
    private double latitude;

    //Внутренний радиус. Вход в который будет засчитано как достижение геоточки.
    @Column(name = "geo_point_radius")
    private int geoPointRadius;

    //Сообщения, при входе в радиус
    @Column(name = "on_point_messages", length = 4096)
    private String onPointMessages;

    //Средний радиус
    @Column(name = "geo_point_middle_radius")
    private int geoPointMeddleRadius;

    //Сообщения, при входе в радиус
    @Column(name = "geo_Point_Meddle_Radius_Reached_Messages", length = 4096)
    private String geoPointMeddleRadiusReachedMessages;

    //Внешний радиус
    @Column(name = "geo_point_outer_radius")
    private int geoPointOuterRadius;

    //Сообщения, при входе в радиус
    @Column(name = "geo_Point_Outer_Radius_Reached_Messages", length = 4096)
    private String geoPointOuterRadiusReachedMessages;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPoint geoPoint = (GeoPoint) o;
        return id == geoPoint.id && Double.compare(geoPoint.longitude, longitude) == 0 && Double.compare(geoPoint.latitude, latitude) == 0 && geoPointRadius == geoPoint.geoPointRadius && geoPointMeddleRadius == geoPoint.geoPointMeddleRadius && geoPointOuterRadius == geoPoint.geoPointOuterRadius && Objects.equals(nodeId, geoPoint.nodeId) && Objects.equals(onPointMessages, geoPoint.onPointMessages) && Objects.equals(geoPointMeddleRadiusReachedMessages, geoPoint.geoPointMeddleRadiusReachedMessages) && Objects.equals(geoPointOuterRadiusReachedMessages, geoPoint.geoPointOuterRadiusReachedMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nodeId, longitude, latitude, geoPointRadius, onPointMessages, geoPointMeddleRadius, geoPointMeddleRadiusReachedMessages, geoPointOuterRadius, geoPointOuterRadiusReachedMessages);
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
                ", nodeId=" + nodeId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", geoPointRadius=" + geoPointRadius +
                ", onPointMessages='" + Boolean.toString(onPointMessages == null) + '\'' +
                ", geoPointMeddleRadius=" + geoPointMeddleRadius +
                ", geoPointMeddleRadiusReachedMessages='" + Boolean.toString(geoPointMeddleRadiusReachedMessages == null) + '\'' +
                ", geoPointOuterRadius=" + geoPointOuterRadius +
                ", geoPointOuterRadiusReachedMessages='" + Boolean.toString(geoPointOuterRadiusReachedMessages == null) + '\'' +
                '}';
    }
}

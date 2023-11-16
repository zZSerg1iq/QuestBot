package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "geopoints")
@Data
public class GeoPoint {

    private final int MAX_LEN = 4096;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double longitude;

    @Column
    private double latitude;

    //радиус самой точки
    @Column(name = "self_radius")
    private int selfRadius;
    //сообщения, при достижении точки
    @JoinColumn(name = "id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestMessage> arrivedMessages;

    //Средний радиус
    @Column(name = "geo_point_outer_radius")
    private int geoPointOuterRadius;
    //Сообщения, при входе в радиус
    @JoinColumn(name = "id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestMessage> outerMessages;

    //Внешний радиус
    @Column(name = "geo_point_external_radius")
    private int geoPointExternalRadius;
    //сообщения, при достижении точки
    @JoinColumn(name = "id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestMessage> externalMessages;


}

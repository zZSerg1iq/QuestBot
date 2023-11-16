package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "quest_node_geopoint")
@Data
public class GeoPoint {

    private final int MAX_LEN = 4096;

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

    @Column(name = "self_radius")
    private int selfRadius;


    @Column(name = "arrived_messages")
    @OneToMany(mappedBy = "")
    private List<ModeMessage> arrivedMessages;


    //Средний радиус
    @Column(name = "geo_point_outer_radius")
    private int geoPointOuterRadius;

    //Сообщения, при входе в радиус
    @Column(name = "outer_radius_messages", length = MAX_LEN)
    private String outerRadiusMessages;

    //Внешний радиус
    @Column(name = "geo_point_external_radius")
    private int geoPointExternalRadius;

    //Сообщения, при входе в радиус
    @Column(name = "external_radius_Messages", length = MAX_LEN)
    private String externalRadiusMessages;


}

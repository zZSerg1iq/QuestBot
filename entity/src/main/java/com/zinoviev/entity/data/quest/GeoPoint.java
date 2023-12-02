package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "geopoints")
@Data
public class GeoPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double longitude;

    @Column
    private double latitude;

    //радиус самой точки
    @Column(name = "point_radius")
    private int selfRadius;
    @OneToMany(mappedBy = "arrivedMessages", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<QuestInfoMessage> arrivedMessages;

    @Column(name = "outer_radius")
    private int geoPointOuterRadius;
    @OneToMany(mappedBy = "outerMessages", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<QuestInfoMessage> outerMessages;

    @Column(name = "external_radius")
    private int geoPointExternalRadius;
    @OneToMany(mappedBy = "externalMessages", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<QuestInfoMessage> externalMessages;

    @OneToOne
    @JoinColumn(name = "geo_point", referencedColumnName = "id")
    private QuestNode questNode;

}

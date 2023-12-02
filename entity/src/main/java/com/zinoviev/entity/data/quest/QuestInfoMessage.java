package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "quest_info_mesages")
@Data
public class QuestInfoMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;

    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "quest_node_messages", referencedColumnName = "id")
    private QuestNode questNodeMessages;

    @ManyToOne
    @JoinColumn(name = "arrived_messages", referencedColumnName = "id")
    private GeoPoint arrivedMessages;

    @ManyToOne
    @JoinColumn(name = "outer_messages", referencedColumnName = "id")
    private GeoPoint outerMessages;

    @ManyToOne
    @JoinColumn(name = "external_messages", referencedColumnName = "id")
    private GeoPoint externalMessages;
}


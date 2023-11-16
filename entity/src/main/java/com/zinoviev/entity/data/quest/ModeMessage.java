package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "quest_node_geopoint")
@Data
public class ModeMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "node_id")
    private long nodeId;
}

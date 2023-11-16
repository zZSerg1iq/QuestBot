package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "main_mesages")
@Data
public class QuestMessage {

    private final int MAX_MESSAGE_LEN = 4096;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "message", length = MAX_MESSAGE_LEN)
    private String message;

    @Lob
    private byte[] image;
}

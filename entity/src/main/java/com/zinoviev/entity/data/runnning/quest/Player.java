package com.zinoviev.entity.data.runnning.quest;

import com.zinoviev.entity.data.User;
import com.zinoviev.entity.enums.GameRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "players")
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @MapsId
    private User userId;

    @ManyToOne
    @MapsId
    private ActiveQuest activeQuest;

    @Column(name = "role")
    private GameRole gameRole;

    @Column(name = "score")
    private int score;

}

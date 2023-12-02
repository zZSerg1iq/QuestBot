package com.zinoviev.entity.data.quest;

import com.zinoviev.entity.data.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "quests")
@Data
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "quest_name")
    private String questName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User author;

    @OneToOne
    private QuestNode firstNode;

}
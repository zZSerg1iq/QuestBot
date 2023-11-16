package com.zinoviev.entity.data.quest;

import com.zinoviev.entity.data.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "quests")
@Data
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Column(name = "quest_name")
    private String questName;

    //@OneToMany(mappedBy = "questId", fetch = FetchType.LAZY)
    //private List<QuestNode> startNode;

    //@Column(name = "shared")
    //@Enumerated(EnumType.STRING)
    //private SharedQuest sharedType;

    //@Column(name = "first_Player_Completes_The_Quest")
    //private boolean firstPlayerCompletesTheQuest;

    //@Column(name = "cost")
    //private double cost;

}
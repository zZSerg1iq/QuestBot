package com.zinoviev.entity.data.runnning.quest;

import com.zinoviev.entity.data.quest.Quest;
import com.zinoviev.entity.data.quest.QuestNode;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "active_quests")
@Data
public class ActiveQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "invite_link", unique = true)
    private String inviteLink;

    @OneToMany(mappedBy = "activeQuest", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    @OneToOne
    @JoinColumn(name = "quest", referencedColumnName = "id")
    private Quest quest;

    @OneToOne
    @JoinColumn(name = "lead_node", referencedColumnName = "id")
    private QuestNode leadNode;

}

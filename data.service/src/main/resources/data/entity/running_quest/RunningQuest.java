package com.zinoviev.questbot.OLD.data.entity.running_quest;

import com.zinoviev.sandbox.data.entity.quest.Quest;
import com.zinoviev.sandbox.data.entity.quest.QuestNode;
import com.zinoviev.sandbox.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "running_quest")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RunningQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    private User ownerId;

    @Column(name = "quest_public_link", unique = true)
    private String questPublicLink;

    @OneToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    private Quest questId;

    @OneToOne
    private QuestNode currentQuestNode;

    @OneToMany(mappedBy="runningQuest", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<RunningQuestMembers> members;

    @Column(name = "first_Player_Completes_The_Quest")
    private boolean firstPlayerCompletesTheQuest;

    @Override
    public String toString() {
        return "RunningQuest{" +
                "id=" + id +
                ", questPublicLink='" + questPublicLink + '\'' +
                ", members=" + members +
                '}';
    }
}
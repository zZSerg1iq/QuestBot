package com.zinoviev.questbot.OLD.data.entity.quest;

import com.zinoviev.sandbox.bot.entity.SharedQuest;
import com.zinoviev.sandbox.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Column(name = "name")
    private String questName;

    @OneToMany(mappedBy = "questId", fetch = FetchType.LAZY)
    private List<QuestNode> startNode;

    @Column(name = "shared")
    @Enumerated(EnumType.STRING)
    private SharedQuest sharedType;

    @Column(name = "first_Player_Completes_The_Quest")
    private boolean firstPlayerCompletesTheQuest;

    @Column(name = "cost")
    private double cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return Double.compare(quest.cost, cost) == 0 && Objects.equals(id, quest.id) && Objects.equals(author, quest.author) && Objects.equals(questName, quest.questName) && Objects.equals(startNode, quest.startNode) && sharedType == quest.sharedType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, questName, startNode, sharedType, cost);
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + questName + '\'' +
                ", shared=" + sharedType +
                ", cost=" + cost +
                '}';
    }
}
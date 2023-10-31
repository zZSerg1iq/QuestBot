package com.zinoviev.questbot.OLD.data.entity.quest;

import com.zinoviev.sandbox.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "quest_owners")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestOwners {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    private Quest quest;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;


    @Override
    public String toString() {
        return "QuestOwners{" +
                "id=" + id +
                '}';
    }
}
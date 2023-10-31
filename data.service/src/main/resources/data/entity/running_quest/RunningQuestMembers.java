package com.zinoviev.questbot.OLD.data.entity.running_quest;

import com.zinoviev.sandbox.data.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "quest_members")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RunningQuestMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = -1L;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "running_quest_id")
    private RunningQuest runningQuest;

    @Column(name = "active")
    private boolean active;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private RunningQuestMuteUserState runningQuestMutedUserState;

    @Column(name = "out_Radius_Reached")
    private boolean outRadiusReached = false;
/*    @Column(name = "out_Radius_Mess_Received")
    private boolean outRadiusMessReceived = false;*/

    @Column(name = "mid_Radius_Reached")
    private boolean midRadiusReached = false;
/*    @Column(name = "mid_Radius_Mess_Received")
    private boolean midRadiusMessReceived = false;*/

    @Column(name = "point_Reached")
    private boolean pointReached = false;
/*    @Column(name = "point_Radius_Mess_Received")
    private boolean pointRadiusMessReceived = false;*/

    @Column(name = "last_Distance_From_The_Point")
    private double lastDistanceFromThePoint = 0;


    @Override
    public String toString() {
        return "RunningQuestMembers{" +
                "id=" + id +
                ", active=" + active +
                ", runningQuestMutedUserState=" + runningQuestMutedUserState +
                '}';
    }
}
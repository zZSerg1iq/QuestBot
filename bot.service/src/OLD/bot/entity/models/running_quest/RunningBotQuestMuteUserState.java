package com.zinoviev.questbot.OLD.bot.entity.models.running_quest;

import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMembers;
import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMuteUserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "muted_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RunningBotQuestMuteUserState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "questMember_id", referencedColumnName = "id")
    private RunningQuestMembers muteState;

    @Column(name = "mute_start_time")
    private int muteStartMessageTime = 0;

    @Column(name = "mute_end_time")
    private int muteEndMessageTime = 0;

    public RunningBotQuestMuteUserState(RunningQuestMuteUserState runningQuestMutedUserState) {
        this.id = runningQuestMutedUserState.getId();
        this.muteStartMessageTime = runningQuestMutedUserState.getMuteStartMessageTime();
        this.muteEndMessageTime = runningQuestMutedUserState.getMuteEndMessageTime();
    }

    @Override
    public String toString() {
        return "UserMute{" +
                "muteStartMessageTime=" + muteStartMessageTime +
                ", muteEndMessageTime=" + muteEndMessageTime +
                '}';
    }
}

package com.zinoviev.questbot.OLD.bot.entity.models.running_quest;

import com.zinoviev.sandbox.bot.entity.models.user.BotUser;
import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMembers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RunningBotQuestMember {

    private Long id;

    private BotUser user;

    private boolean active;

    private RunningBotQuestMuteUserState muteUserState;

    private HashSet<String> expectedUserAnswers;
    private int answerCount = 0;

    private boolean outRadiusReached = false;
    private boolean outRadiusMessReceived = false;

    private boolean midRadiusReached = false;
    private boolean midRadiusMessReceived = false;

    private boolean pointReached = false;
    private boolean pointMessReceived = false;

    private double lastDistanceFromThePoint = 0;

    public RunningBotQuestMember(RunningQuestMembers member) {
        this.id = member.getId();
        this.user = new BotUser(member.getUser());
        this.active = member.isActive();
        this.outRadiusReached = member.isOutRadiusReached();
        //this.outRadiusMessReceived = member.isOutRadiusMessReceived();
        this.midRadiusReached = member.isMidRadiusReached();
        //this.midRadiusMessReceived = member.isMidRadiusMessReceived();
        this.pointReached = member.isPointReached();
        //this.pointMessReceived = member.isPointRadiusMessReceived();
        this.lastDistanceFromThePoint = member.getLastDistanceFromThePoint();

        if (member.getRunningQuestMutedUserState() != null){
                this.muteUserState = new RunningBotQuestMuteUserState(member.getRunningQuestMutedUserState());
        }
    }



    @Override
    public String toString() {
        return "RunningBotQuestMember{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", active=" + active +
                ", muteUserState=" + muteUserState +
                '}';
    }
}
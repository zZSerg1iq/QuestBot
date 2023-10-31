package com.zinoviev.questbot.OLD.bot.entity.models.running_quest;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuestNode;
import com.zinoviev.sandbox.data.entity.running_quest.RunningQuest;
import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMembers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RunningBotQuest {

    private Long id;

    private long ownerId; //для того что бы можно было остановить квест, если в него никто не играет

    private String questPublicLink; //ссылка - приглашение на участие, она же уникальный идентификатор конкретного запущенного квеста

    private long questId;  //квест, из которого брать ноды

    private BotQuestNode currentNode;  // id текущей ноды квеста.

    private List<RunningBotQuestMember> members; //список участников (используется админами квеста для операций над игроками)

    private boolean firstPlayerCompletesTheQuest;

    public RunningBotQuest(RunningQuest runningQuest) {
        this.id = runningQuest.getId();
        this.ownerId = runningQuest.getOwnerId().getId();
        this.questPublicLink = runningQuest.getQuestPublicLink();
        this.questId = runningQuest.getQuestId().getId();
        this.firstPlayerCompletesTheQuest = runningQuest.isFirstPlayerCompletesTheQuest();
        this.currentNode = new BotQuestNode(runningQuest.getCurrentQuestNode());

        members = new ArrayList<>();
        if (runningQuest.getMembers() != null) {
            List<RunningQuestMembers> dataMembers = runningQuest.getMembers();

            if (dataMembers != null) {
                for (var member : dataMembers) {
                    members.add(new RunningBotQuestMember(member));
                 }
            }
        }

    }

    @Override
    public String toString() {
        return "RunningBotUserQuest{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", questPublicLink='" + questPublicLink + '\'' +
                ", questId=" + questId +
                ", currentNode=" + currentNode +
                ", members=" + members +
                '}';
    }
}
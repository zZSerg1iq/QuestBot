package com.zinoviev.questbot.OLD.bot.entity.models.quest;

import com.zinoviev.sandbox.bot.entity.SharedQuest;
import com.zinoviev.sandbox.data.entity.quest.Quest;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Objects;

@Getter
@Setter
public class BotQuest implements BotQuestInterface {

    private Long id = -1L;

    private Long author;

    private String name;

    private LinkedList<BotQuestNode> theQuestQueue = new LinkedList<>();

    private SharedQuest shared;

    private boolean firstPlayerCompletesTheQuest;

    private double cost;

    public BotQuest() {
        theQuestQueue = new LinkedList<>();
    }

    public BotQuest(Quest quest) {
        if (quest != null) {
            this.id = quest.getId();
            this.name = quest.getQuestName();
            this.author = quest.getAuthor().getTelegramId();
            this.shared = quest.getSharedType();
            this.cost = quest.getCost();
            this.firstPlayerCompletesTheQuest = quest.isFirstPlayerCompletesTheQuest();
        }
   }

    public BotQuest(LinkedList<BotQuestNode> theQuest, Long id, Long author, String name, SharedQuest shared, double cost) {
        this.theQuestQueue = theQuest == null? new LinkedList<>(): theQuest;
        this.id = id;
        this.author = author;
        this.name = name;
        this.shared = shared;
        this.cost = cost;
    }


    @Override
    public void skip(int skipCount) {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotQuest that = (BotQuest) o;
        return Double.compare(that.cost, cost) == 0 && theQuestQueue.equals(that.theQuestQueue) && Objects.equals(id, that.id) && author.equals(that.author) && name.equals(that.name) && shared == that.shared;
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, name, shared, cost);
    }

    @Override
    public void addQuestPart(BotQuestNode botQuestNode) {
        theQuestQueue.add(botQuestNode);
    }

    @Override
    public String toString() {
        return "BotUserQuest{" +
                "theQuest=" + theQuestQueue.size() +
                ", id=" + id +
                "------------"+theQuestQueue+
                ", author=" + author +
                ", name='" + name + '\'' +
                ", shared=" + shared +
                ", cost=" + cost +
                '}';
    }
}
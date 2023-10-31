package com.zinoviev.questbot.OLD.data.service.impl;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuestNode;
import com.zinoviev.sandbox.data.entity.quest.QuestNode;
import com.zinoviev.sandbox.data.repository.QuestNodeRepository;
import com.zinoviev.sandbox.data.service.QuestNodeRepositoryService;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class QuestNodeRepositoryServiceImpl implements QuestNodeRepositoryService {

    private final QuestNodeRepository questNodeRepository;

    public QuestNodeRepositoryServiceImpl(QuestNodeRepository questNodeRepository) {
        this.questNodeRepository = questNodeRepository;
    }


    @Override
    public QuestNode findQuestNodeById(long nodeId) {
        return questNodeRepository.findById(nodeId);
    }

    @Override
    public BotQuestNode findBotQuestNodeById(long nodeId) {
        return findByQuest_Id(findQuestNodeById(nodeId).getQuestId().getId());
    }

    @Override
    public BotQuestNode findByQuest_Id(long questId) {
        List<QuestNode> questNodes = questNodeRepository.findByQuestId_Id(questId);
        LinkedList<BotQuestNode> botQuestNodes = new LinkedList<>();

        for (var node: questNodes) {
            botQuestNodes.add(new BotQuestNode(node));
        }

        for (int i = 0; i < botQuestNodes.size() - 2; i++) {
            botQuestNodes.get(i).setNextNode(botQuestNodes.get(i+1));
        }
         return botQuestNodes.get(0);
    }

    public BotQuestNode findByQuest_IdAndNodeId(long questId, long nodeId) {
        List<QuestNode> questNodes = questNodeRepository.findByQuestId_Id(questId);
        LinkedList<BotQuestNode> botQuestNodes = new LinkedList<>();

        for (var node: questNodes) {
            botQuestNodes.add(new BotQuestNode(node));
        }

        int nodeIndex = 0;

        for (int i = 0; i < botQuestNodes.size() - 2; i++) {
            if (botQuestNodes.get(i).getId() == nodeId){
                nodeIndex = i;
            }
            botQuestNodes.get(i).setNextNode(botQuestNodes.get(i+1));
        }
        return botQuestNodes.get(nodeIndex);
    }
}

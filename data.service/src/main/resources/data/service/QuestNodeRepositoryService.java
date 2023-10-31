package com.zinoviev.questbot.OLD.data.service;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuestNode;
import com.zinoviev.sandbox.data.entity.quest.QuestNode;


public interface QuestNodeRepositoryService {

    QuestNode findQuestNodeById(long nodeId);

    BotQuestNode findBotQuestNodeById(long nodeId);

    BotQuestNode findByQuest_Id(long id);

    BotQuestNode findByQuest_IdAndNodeId(long questId, long nodeId);
}

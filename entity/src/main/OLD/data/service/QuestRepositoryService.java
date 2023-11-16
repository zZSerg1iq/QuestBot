package com.zinoviev.sandbox.data.service;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;

import java.util.List;

public interface QuestRepositoryService {

    void saveQuestList(List<BotQuest> quests);

    void saveQuest(BotQuest quest);

    List<BotQuest> getUserQuestList(Long telegramId);

    BotQuest getQuestById(long questId);
}
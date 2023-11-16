package com.zinoviev.sandbox.data.service;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;

import java.util.List;

public interface QuestOwnersRepositoryService{

    List<BotQuest> findAllByOwner_Id(long id);
}

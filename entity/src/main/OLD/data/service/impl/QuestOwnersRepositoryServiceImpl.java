package com.zinoviev.sandbox.data.service.impl;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;
import com.zinoviev.sandbox.data.entity.quest.QuestOwners;
import com.zinoviev.sandbox.data.repository.*;
import com.zinoviev.sandbox.data.service.QuestOwnersRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestOwnersRepositoryServiceImpl implements QuestOwnersRepositoryService {


    private final QuestOwnersRepository questOwnersRepository;


    @Autowired
    public QuestOwnersRepositoryServiceImpl(QuestOwnersRepository questOwnersRepository) {
        this.questOwnersRepository = questOwnersRepository;
    }

    @Override
    public List<BotQuest> findAllByOwner_Id(long id) {
        List<QuestOwners> quests = questOwnersRepository.findAllByOwner_Id(id);
        quests.forEach(questOwners -> System.out.println(questOwners.getQuest()));

        return new ArrayList<>();
    }
}

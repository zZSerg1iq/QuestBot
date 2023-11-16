package com.zinoviev.sandbox.data.service;

import com.zinoviev.sandbox.bot.entity.models.running_quest.RunningBotQuestMember;
import com.zinoviev.sandbox.bot.entity.models.running_quest.RunningBotQuest;

import java.util.List;

public interface RunningQuestRepositoryService {

    boolean isRunningQuestExist(String link);

    RunningBotQuest getRunningQuestByPublicLink(String link);

    void saveRunningQuest(RunningBotQuest runningQuest);

    void saveQuestMembers(List<RunningBotQuestMember> botQuestMembers, String questPublicLink);

    List<RunningBotQuest> getAll();

    void deleteByQuestPublicLink(String publicLink);

}

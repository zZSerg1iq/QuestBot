package com.zinoviev.questbot.OLD.bot.init;

import com.zinoviev.sandbox.bot.QuestBot;
import com.zinoviev.sandbox.bot.quest_management.RunningQuestManager;
import com.zinoviev.sandbox.data.service.RunningQuestRepositoryService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class InitClass {
    private final QuestBot bot;

    @Autowired
    public InitClass(QuestBot bot, RunningQuestRepositoryService runningQuestService) {
        this.bot = bot;
        RunningQuestManager.getInstance().setServices(runningQuestService);
        RunningQuestManager.getInstance().fillTheCacheOfRunningQuests();
    }

    @SneakyThrows
    @PostConstruct
    public void init(){
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
    }
}

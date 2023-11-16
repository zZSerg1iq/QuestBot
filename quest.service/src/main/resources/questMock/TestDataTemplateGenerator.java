package com.zinoviev.sandbox.templateData;


import com.zinoviev.sandbox.bot.entity.Role;
import com.zinoviev.sandbox.bot.entity.SignInStatus;
import com.zinoviev.sandbox.bot.entity.models.user.BotUser;
import com.zinoviev.sandbox.data.service.impl.QuestRepositoryServiceImpl;
import com.zinoviev.sandbox.data.service.impl.RunningQuestRepositoryServiceImpl;
import com.zinoviev.sandbox.data.service.impl.UserRepositoryServiceImpl;

public class TestDataTemplateGenerator {

    private final UserRepositoryServiceImpl userService;
    private final QuestRepositoryServiceImpl questService;
    private final RunningQuestRepositoryServiceImpl runningQuestService;

    public TestDataTemplateGenerator(UserRepositoryServiceImpl userService, QuestRepositoryServiceImpl questService, RunningQuestRepositoryServiceImpl runningQuestService) {
        this.userService = userService;
        this.questService = questService;
        this.runningQuestService = runningQuestService;
    }

    public void createRunningQuestAndSetAdmin(BotUser botUser){
        botUser.setRole(Role.ADMIN);
        botUser.setUserStatus(SignInStatus.SIGN_IN_COMPLETE);
        botUser.setAvatarName("AdminName");

       // botUser.setUserQuests(BotQuestTemplateGenerator.getQuestList(botUser.getId())); //!!!!!!!!!!!!!!!!!!
    //    botUser.setQuestPublicLink();
    }

    public void createRunningQuestAndSetPlayer(BotUser botUser){
        botUser.setRole(Role.PLAYER);
        botUser.setUserStatus(SignInStatus.SIGN_IN_COMPLETE);
        botUser.setAvatarName("PlayerName");
    }
}

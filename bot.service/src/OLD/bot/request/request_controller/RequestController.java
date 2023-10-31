package com.zinoviev.questbot.OLD.bot.request.request_controller;

import com.zinoviev.sandbox.bot.bot_dispatcher.TelegramBotController;
import com.zinoviev.sandbox.bot.entity.Role;
import com.zinoviev.sandbox.bot.entity.models.user.BotUser;
import com.zinoviev.sandbox.bot.request.request_handlers.admin_role_handlers.AdminRoleHandler;
import com.zinoviev.sandbox.bot.request.request_handlers.player_role_handlers.PlayerRoleHandler;
import com.zinoviev.sandbox.bot.request.request_handlers.user_role_handlers.UserRoleHandler;
import com.zinoviev.sandbox.data.service.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;


public class RequestController extends Thread{

    private final int threadIndex;


    private final UserRepositoryService userService;
    private final QuestRepositoryService questService;
    private final RunningQuestRepositoryService runningQuestService;
    private final QuestOwnersRepositoryService questOwnersRepositoryService;
    private final QuestNodeRepositoryService nodeRepositoryService;



    private final RequestControllerFactory requestControllerFactory;
    private final TelegramBotController botController;



    @Getter
    private final HashSet<Long> usersId;
    private final int USER_HANDLER_MAX_COUNT = 20;

    public RequestController(int index, UserRepositoryService userService, QuestRepositoryService questService, RunningQuestRepositoryService runningQuestService, QuestOwnersRepositoryService questOwnersRepositoryService, QuestNodeRepositoryService nodeRepositoryService, RequestControllerFactory requestControllerFactory, TelegramBotController botController) {
        this.threadIndex = index;
        this.userService = userService;
        this.questService = questService;
        this.runningQuestService = runningQuestService;
        this.questOwnersRepositoryService = questOwnersRepositoryService;
        this.nodeRepositoryService = nodeRepositoryService;

        this.requestControllerFactory = requestControllerFactory;
        this.botController = botController;

        usersId = new HashSet<>(USER_HANDLER_MAX_COUNT, 1);
    }


    @Override
    @SneakyThrows
    public void run() {
        Update update;

        while (!interrupted()){
            System.out.println("Thread "+threadIndex+": working...");

            update = requestControllerFactory.getRequest();

            if (update != null){
                requestValidation(update);
            }
            Thread.sleep(10);
        }
    }


    public void requestValidation(Update update) {
        //Получаем id того, кто пишет сообщение и проверяем регистрацию
        long id = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getFrom().getId();
        BotUser botUser = userService.getBotUserByTelegramId(id);

        //если он прислал файл, скачать и обработать
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            botController.downloadQuestFile(update);
        }

        //если он прислал какое-то просто сообщение, то отдать его менеджеру роли
        Role role = botUser.getRole();

        switch (role) {
            case ADMIN -> {
                new AdminRoleHandler(userService, questService, runningQuestService, botController, botUser).action(update);
            }
            case PLAYER -> {
                new PlayerRoleHandler(userService, questService, runningQuestService, botController, botUser).action(update);
            }
            default -> {
                new UserRoleHandler(userService, questService, questOwnersRepositoryService, runningQuestService, nodeRepositoryService, botController, botUser).action(update);
            }
        }

    }


}

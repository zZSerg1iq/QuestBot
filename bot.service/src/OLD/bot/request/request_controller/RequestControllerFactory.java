package com.zinoviev.questbot.OLD.bot.request.request_controller;

import com.zinoviev.sandbox.bot.bot_dispatcher.TelegramBotController;
import com.zinoviev.sandbox.data.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RequestControllerFactory {

    private final UserRepositoryService userService;
    private final QuestRepositoryService questService;
    private final RunningQuestRepositoryService runningQuestService;
    private final QuestOwnersRepositoryService questOwnersRepositoryService;
    private final QuestNodeRepositoryService nodeRepositoryService;



    private final ConcurrentLinkedDeque<Update> requestQueue;


    private int threadCount = 0;
    private final ExecutorService executorService;
    private final ReentrantLock lock;
    private final Condition isNonEmpty;

    private TelegramBotController botController;


    @Autowired
    private RequestControllerFactory(UserRepositoryService userService, QuestRepositoryService questService, RunningQuestRepositoryService runningQuestService, QuestOwnersRepositoryService questOwnersRepositoryService, QuestNodeRepositoryService nodeRepositoryService) {
        this.userService = userService;
        this.questService = questService;
        this.runningQuestService = runningQuestService;
        this.questOwnersRepositoryService = questOwnersRepositoryService;
        this.nodeRepositoryService = nodeRepositoryService;

        lock = new ReentrantLock();
        isNonEmpty = lock.newCondition();
        executorService = Executors.newCachedThreadPool();

        requestQueue = new ConcurrentLinkedDeque<>();
    }

    public void setBotController(TelegramBotController botController) {
        this.botController = botController;
        executorService.execute( new RequestController(++threadCount, userService, questService, runningQuestService, questOwnersRepositoryService, nodeRepositoryService, this, botController));
    }

    public void addRequest(Update update) {
        lock.lock();
        try {
            requestQueue.add(update);
            isNonEmpty.signal();
        } finally {
            lock.unlock();
        }

        checkQueueSize();
    }

    private void checkQueueSize() {
        if (requestQueue.size() > 20){
            executorService.execute( new RequestController(++threadCount, userService, questService, runningQuestService, questOwnersRepositoryService, nodeRepositoryService, this, botController));
        }
    }

    public Update getRequest(){
        lock.lock();
        Update update = null;
        try {
            while (requestQueue.isEmpty()) {
                isNonEmpty.await();
            }
            update = requestQueue.poll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return update;
    }


}

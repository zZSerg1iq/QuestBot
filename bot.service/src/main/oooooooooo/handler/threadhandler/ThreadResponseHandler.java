package com.zinoviev.bot.handler.threadhandler;

import com.zinoviev.bot.controller.impl.SimpleTelegramController;
import com.zinoviev.bot.entity.rest.UpdateData;
import com.zinoviev.bot.handler.ResponseHandler;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Component("ThreadHandler")
public class ThreadResponseHandler implements ResponseHandler {


    private final SimpleTelegramController telegramController;

    private final LinkedList<UpdateData> dbResponseQueue;

    private final ExecutorService executorService;

    private final ReentrantLock lock;
    private final Condition isNonEmpty;

    private int threadCount = 0;

    public ThreadResponseHandler(SimpleTelegramController telegramController) {
        this.telegramController = telegramController;
        dbResponseQueue = new LinkedList<>();

        executorService = Executors.newCachedThreadPool();
        lock = new ReentrantLock();
        isNonEmpty = lock.newCondition();
        checkQueueSize();
    }

    @Override
    public void addDbResponse(UpdateData updateData) {
        lock.lock();
        try {
            dbResponseQueue.add(updateData);
            isNonEmpty.signal();
        } finally {
            lock.unlock();
        }

        checkQueueSize();
    }


    public UpdateData getRequest(){
        lock.lock();
        UpdateData updateData = null;
        try {
            while (dbResponseQueue.isEmpty()) {
                isNonEmpty.await();
            }
            updateData = dbResponseQueue.poll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return updateData;
    }

    private void checkQueueSize() {
        if (threadCount == 0 || dbResponseQueue.size() > 20){
            Thread thread = new HandlerThread(++threadCount, this, telegramController);
            thread.setDaemon(true);
            executorService.execute(thread);
        }
    }
}

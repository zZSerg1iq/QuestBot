package com.zinoviev.bot.handler.threadhandler;

import com.zinoviev.bot.controller.update.TelegramController;
import com.zinoviev.bot.entity.SignInStatus;
import com.zinoviev.bot.entity.rest.UpdateData;
import com.zinoviev.bot.service.role.user_role_handlers.UserRoleHandler;
import com.zinoviev.bot.service.UserRegService;
import lombok.SneakyThrows;

public class HandlerThread extends Thread {

    private final int threadIndex;
    private final ThreadResponseHandler threadUpdateHandler;
    private final TelegramController telegramController;

    private final UserRoleHandler userRoleHandler;


    public HandlerThread(int index, ThreadResponseHandler threadUpdateHandler, TelegramController telegramController) {
        this.threadIndex = index;
        this.threadUpdateHandler = threadUpdateHandler;
        this.telegramController = telegramController;
        userRoleHandler = new UserRoleHandler(telegramController);
    }


    @Override
    @SneakyThrows
    public void run() {
        UpdateData updateData;

        while (!interrupted()) {
            updateData = threadUpdateHandler.getRequest();
            if (updateData != null) {
                requestValidation(updateData);
            }
        }
    }


    public void requestValidation(UpdateData updateData) {
        if (updateData.getUserData().getSignInStatus() != SignInStatus.SIGN_IN_COMPLETE) {
            new UserRegService(telegramController).proceedSignUp(updateData);
            return;
        }

        new UserRoleHandler(telegramController).actionHandler(updateData);
    }


}

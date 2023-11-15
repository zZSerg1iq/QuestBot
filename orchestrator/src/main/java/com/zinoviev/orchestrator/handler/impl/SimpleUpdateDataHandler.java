package com.zinoviev.orchestrator.handler.impl;

import com.zinoviev.entity.enums.SignInStatus;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.handler.UpdateDataHandler;
import com.zinoviev.orchestrator.service.SignUpService;
import com.zinoviev.orchestrator.service.user.UserRoleHandler;
import org.springframework.stereotype.Component;


@Component
public class SimpleUpdateDataHandler implements UpdateDataHandler {

    private final DataExchangeController exchangeController;
    private final UserRoleHandler userRoleHandler;

    public SimpleUpdateDataHandler(DataExchangeController exchangeController) {
        this.exchangeController = exchangeController;
        this.userRoleHandler = new UserRoleHandler(exchangeController);
    }

    @Override
    public void addRequest(UpdateData updateData) {
        System.out.println("add request :   is user logged in?");
        if (updateData.getUserData().getSignInStatus() != SignInStatus.SIGN_UP_COMPLETE) {
            new SignUpService(exchangeController).proceedSignUp(updateData);
            return;
        }

        System.out.println("add request :   user is logged in");
        userRoleHandler.actionHandler(updateData);
    }


}

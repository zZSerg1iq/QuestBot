package com.zinoviev.orchestrator.handler.impl;

import com.zinoviev.entity.enums.SignInStatus;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.handler.UpdateDataHandler;
import com.zinoviev.orchestrator.service.UserRegService;
import org.springframework.stereotype.Component;


@Component
public class SimpleUpdateDataDataHandler implements UpdateDataHandler {

    private final DataExchangeController exchangeController;

    public SimpleUpdateDataDataHandler(DataExchangeController exchangeController) {
        this.exchangeController = exchangeController;
    }

    @Override
    public void addRequest(UpdateData updateData) {
        System.out.println(updateData);
        if (updateData.getUserData().getSignInStatus() != SignInStatus.SIGN_IN_COMPLETE) {
            new UserRegService(exchangeController).proceedSignUp(updateData);
            return;
        }

      //  new UserRoleHandler().actionHandler(updateData);
    }


}

package com.zinoviev.data.init;

import com.zinoviev.data.controller.ExchangeController;
import com.zinoviev.data.handler.RequestHandlerService;
import com.zinoviev.data.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Init {
    private final ExchangeController exchangeController;
    private final UserRepositoryService userRepositoryService;
    private RequestHandlerService requestHandlerService;

    @Autowired
    public Init(ExchangeController exchangeController, UserRepositoryService userRepositoryService) {
        this.exchangeController = exchangeController;
        this.userRepositoryService = userRepositoryService;
        requestHandlerService = new RequestHandlerService(userRepositoryService, exchangeController);
        exchangeController.setRequestService(requestHandlerService);
    }
}

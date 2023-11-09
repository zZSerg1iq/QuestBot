package com.zinoviev.data.init;

import com.zinoviev.data.controller.DBResponseController;
import com.zinoviev.data.handler.RequestHandlerService;
import com.zinoviev.data.handler.UserRequestHandlerService;
import com.zinoviev.data.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Init {
    private final DBResponseController dbResponseController;

    private final UserRepositoryService userRepositoryService;
    private RequestHandlerService requestHandlerService;

    @Autowired
    public Init(DBResponseController dbResponseController, UserRepositoryService userRepositoryService) throws InterruptedException {
        this.dbResponseController = dbResponseController;
        this.userRepositoryService = userRepositoryService;
        requestHandlerService = new UserRequestHandlerService(userRepositoryService, dbResponseController);
        dbResponseController.setRequestService(requestHandlerService);

      //  dbResponseController.sendUserDataResponse(new UpdateData());
    }
}

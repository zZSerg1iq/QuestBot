package com.zinoviev.data.handler;

import com.zinoviev.data.controller.DBResponseController;
import com.zinoviev.data.service.UserRepositoryService;
import com.zinoviev.entity.enums.RequestStatus;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRequestHandlerService implements RequestHandlerService {

    private final UserRepositoryService userRepositoryService;

    private final DBResponseController dbResponseController;


    @Autowired
    public UserRequestHandlerService(UserRepositoryService userRepositoryService, DBResponseController dbResponseController) {
        this.userRepositoryService = userRepositoryService;

        this.dbResponseController = dbResponseController;
    }




    public void processTheRequest(UpdateData updateData){
        if (updateData.getRequestStatus() == RequestStatus.SAVE_ONLY){
            System.out.println( updateData.getUserData().getSignInStatus() );
            userRepositoryService.saveUser(updateData.getUserData());
        } else {
            UserData userData = userRepositoryService.getUserDataByTelegramId(updateData);

            updateData.setUserData(userData);
            dbResponseController.sendUserDataResponse(updateData);
        }
    }
}

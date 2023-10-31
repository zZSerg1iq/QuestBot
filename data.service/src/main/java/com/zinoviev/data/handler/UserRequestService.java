package com.zinoviev.data.handler;

import com.zinoviev.data.controller.out.DBResponseController;
import com.zinoviev.data.entity.RequestStatus;
import com.zinoviev.data.service.UserRepositoryService;
import com.zinoviev.data.service.entity.UpdateData;
import com.zinoviev.data.service.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRequestService implements RequestService{

    private final UserRepositoryService userRepositoryService;
    private final DBResponseController dbResponseController;


    @Autowired
    public UserRequestService(UserRepositoryService userRepositoryService, DBResponseController dbResponseController) {
        this.userRepositoryService = userRepositoryService;

        this.dbResponseController = dbResponseController;
    }




    public void processTheRequest(UpdateData updateData){
        System.out.println("БД: обрабатываем...");

        if (updateData.getRequestStatus() == RequestStatus.SAVE_ONLY){
            userRepositoryService.saveUser(updateData.getUserData());
        } else {
            UserData userData = userRepositoryService.getUserDataByTelegramId(updateData);

            updateData.setUserData(userData);
            dbResponseController.sendUserDataResponse(updateData);
        }
    }
}

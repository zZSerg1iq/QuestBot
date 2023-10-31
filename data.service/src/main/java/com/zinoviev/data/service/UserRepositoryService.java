package com.zinoviev.data.service;

import com.zinoviev.data.service.entity.UpdateData;
import com.zinoviev.data.service.entity.UserData;


public interface UserRepositoryService {

    UserData getUserDataByTelegramId(UpdateData updateData);

    public void saveUser(UserData botUser);

    boolean isNamePresent(String text);

}

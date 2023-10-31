package com.zinoviev.data.service;

import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.UserData;


public interface UserRepositoryService {

    UserData getUserDataByTelegramId(UpdateData updateData);

    public void saveUser(UserData botUser);

    boolean isNamePresent(String text);

}

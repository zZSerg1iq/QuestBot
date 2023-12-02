package com.zinoviev.data.service;

import com.zinoviev.entity.dto.data.UserDto;
import com.zinoviev.entity.dto.update.UpdateDto;


public interface UserRepositoryService {

    UserDto getUserDataByTelegramId(UpdateDto updateDto);

    public void saveUser(UserDto botUserDto);


}

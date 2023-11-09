package com.zinoviev.data.service.impl;


import com.zinoviev.data.repository.UserRepository;
import com.zinoviev.data.service.UserRepositoryService;
import com.zinoviev.entity.data.User;
import com.zinoviev.entity.enums.Role;
import com.zinoviev.entity.enums.SignInStatus;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserData getUserDataByTelegramId(UpdateData updateData) {
        User user = userRepository.getUserByTelegramId(updateData.getMessage().getUserId());
        UserData userData;

        if (user == null) {
            user = new User();
            user.setRole(Role.USER);
            user.setSignInStatus(SignInStatus.SIGN_IN_NONE);
            user.setTelegramId(updateData.getMessage().getUserId());
            user.setUserName(updateData.getMessage().getUserName());
            user.setFirstName(updateData.getMessage().getFirstName());
            user.setLastName(updateData.getMessage().getLastName());

            user = userRepository.save(user);
        }

        userData = new UserData(user);

        return userData;
    }

    @Override
    public boolean isNamePresent(String name) {
        return userRepository.getUserByAvatarName(name) != null;
    }


    @Override
    public void saveUser(UserData userData) {
        User user = userRepository.findById(userData.getId()).orElseGet(User::new);

        user.setUserName(userData.getUserName());
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setAvatarName(userData.getAvatarName());
        user.setSignInStatus(userData.getSignInStatus());
        user.setRole(userData.getRole());

        userRepository.save(user);
    }

}

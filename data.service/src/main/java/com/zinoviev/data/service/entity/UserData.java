package com.zinoviev.data.service.entity;

import com.zinoviev.data.entity.Role;
import com.zinoviev.data.entity.SignInStatus;
import com.zinoviev.data.entity.User;
import com.zinoviev.data.entity.Statistics;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UserData {

    private Long id = 0L;

    private Long telegramId;

    private String firstName;

    private String lastName;

    private String userName;

    private String avatarName;

    private Role role;

    private SignInStatus signInStatus;


    private Statistics statistics;

    public UserData(User user) {
        id = user.getId();
        telegramId = user.getTelegramId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        userName = user.getUserName();
        avatarName = user.getAvatarName();
        role = user.getRole();
        signInStatus = user.getSignInStatus();
    }
}

package com.zinoviev.entity.model;

import com.zinoviev.entity.data.User;
import com.zinoviev.entity.enums.Role;
import com.zinoviev.entity.enums.SignInStatus;
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

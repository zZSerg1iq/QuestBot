package com.zinoviev.entity.dto.data;

import com.zinoviev.entity.dto.data.running.quest.PlayerDto;
import com.zinoviev.entity.enums.SignInStatus;
import lombok.Data;


@Data
public class UserDto {

    private long id;

    private long telegramId;

    private String gameName;

    private String telegramName;

    private SignInStatus signInStatus;

    private PlayerDto player;


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", gameName='" + gameName + '\'' +
                ", telegramName='" + telegramName + '\'' +
                ", signInStatus=" + signInStatus +
                ", player=" + player +
                '}';
    }
}

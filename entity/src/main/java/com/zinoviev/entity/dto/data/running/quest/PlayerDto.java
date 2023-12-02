package com.zinoviev.entity.dto.data.running.quest;

import com.zinoviev.entity.enums.GameRole;
import lombok.Data;


@Data
public class PlayerDto {

    private long id;

    private long userId;

    private long activeQuest;

    private GameRole gameRole;

    private int score;

    @Override
    public String toString() {
        return "PlayerDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", activeQuest=" + activeQuest +
                ", gameRole=" + gameRole +
                ", score=" + score +
                '}';
    }
}

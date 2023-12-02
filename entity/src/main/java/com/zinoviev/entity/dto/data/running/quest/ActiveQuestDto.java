package com.zinoviev.entity.dto.data.running.quest;

import com.zinoviev.entity.dto.data.quest.QuestDto;
import com.zinoviev.entity.dto.data.quest.QuestNodeDto;
import lombok.Data;

import java.util.List;


@Data
public class ActiveQuestDto {

    private long id;

    private String inviteLink;

    private List<PlayerDto> players;

    private long quest;

    private long leadNode;


    @Override
    public String toString() {
        return "ActiveQuestDto{" +
                "id=" + id +
                ", inviteLink='" + inviteLink + '\'' +
                ", playerId=" + (players != null? players.toString() : "null")  +
                ", quest=" + quest +
                ", leadNode=" + leadNode +
                '}';
    }
}

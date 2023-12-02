package com.zinoviev.entity.dto.data.quest;

import com.zinoviev.entity.dto.data.UserDto;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class QuestDto {

    private long id;

    private String questName;

    private String description;

    private long author;

    private long firstNode;

    @Override
    public String toString() {
        return "QuestDto{" +
                "id=" + id +
                ", questName='" + questName + '\'' +
                ", author=" + author +
                ", firstNode=" + firstNode +
                '}';
    }
}
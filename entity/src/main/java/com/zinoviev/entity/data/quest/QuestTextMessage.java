package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions_and_answers")
public class QuestTextMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "expected_answers", referencedColumnName = "id")
    private QuestNode expectedAnswers;

    @ManyToOne
    @JoinColumn(name = "correct_messages_react", referencedColumnName = "id")
    private QuestNode correctMessages;

    @ManyToOne
    @JoinColumn(name = "incorrect_messages_react", referencedColumnName = "id")
    private QuestNode incorrectMessages;


}

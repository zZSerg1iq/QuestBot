package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "quest_nodes")
@Data
public class QuestNode {

    private final int MAX_STAGE_NAME_LEN = 512;
    private final int MAX_MESSAGE_LEN = 4096;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "stage_name", length = MAX_STAGE_NAME_LEN)
    private String stageName;

    @ManyToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    private Quest questId;

    @OneToOne
    @JoinColumn(name = "next_node", referencedColumnName = "id")
    private QuestNode nextNode;


    @JoinColumn(name = "id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestMessage> nodeMessages;


    // список ожидаемых ответов
    @Column(name = "user_answers", length = MAX_MESSAGE_LEN)
    private String expectedUserAnswers;

    @Column(name = "number_of_answers")
    private int requiredNumberOfAnswers;

    //реагировать ли на неверные ответы
    @Column(name = "react_on_incorrect_answers")
    private boolean reactOnIncorrectAnswerMessages;

    //сообщения, которыми реагировать. Будет показано случайно выбранное
    @Column(name = "incorrect_answers_react_mess", length = MAX_MESSAGE_LEN)
    private String incorrectAnswersReactMessages;

    //реагировать ли на верные ответы
    @Column(name = "react_On_Correct_Answer_Messages")
    private boolean reactOnCorrectAnswerMessages;

    //сообщения, которыми реагировать. Будет показано случайно выбранное
    @Column(name = "correct_answers_react_mess", length = MAX_MESSAGE_LEN)
    private String correctAnswersReactMessages;

    //геоточка
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id")
    private GeoPoint geoPoint;

    //переключаться ли на новый пункт квеста при достижении основной геоточки
    @Column(name = "switch_on_reached")
    private boolean onReachedMainPointSwitchToNextNode;

    //время, через которое произойдет переключение
    @Column(name = "switch_time")
    private int pauseInSecBeforeSwitchingToTheNextNode;


}

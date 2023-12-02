package com.zinoviev.entity.data.quest;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "quest_nodes")
@Data
public class QuestNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "stage_name")
    private String stageName;

    @ManyToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    private Quest questId;

    @OneToOne
    @JoinColumn(name = "next_node", referencedColumnName = "id")
    private QuestNode nextNode;

    @OneToMany(mappedBy = "questNodeMessages")
    private List<QuestInfoMessage> questMessages;

    @OneToMany(mappedBy = "expectedAnswers")
    private List<QuestTextMessage> expectedUserAnswers;

    @Column(name = "answers_count")
    private int requiredAnswersCount;


    @Column(name = "react_on_correct_answers")
    private boolean reactOnCorrectAnswer;

    @OneToMany(mappedBy = "correctMessages", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuestTextMessage> correctAnswersReactMessages;

    @Column(name = "react_on_wrong_answers")
    private boolean reactOnWrongAnswer;

    @OneToMany(mappedBy = "incorrectMessages", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuestTextMessage> wrongAnswersReactMessages;

    @OneToOne(mappedBy = "questNode", fetch = FetchType.EAGER, orphanRemoval = true)
    private GeoPoint geoPoint;

    @Column(name = "switch_to_next")
    private boolean switchToNextNodeWhenPointReached;

    @Column(name = "pause_before_switch")
    private int pauseBeforeSwitch;


}

package com.zinoviev.sandbox.data.entity.quest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuestNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "stage_name", length = 512)
    private String stageName;

    @OneToOne
    @JoinColumn(name = "next_node", referencedColumnName = "id")
    private QuestNode nextNode;

    @ManyToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id")
    private Quest questId;

    // основные сообщения квестовой точки, выводимые в начале
    @Column(name = "node_messages", length = 4096)
    private String nodeMessages;

    // список ожидаемых ответов
    @Column(name = "user_answers", length = 512)
    private String expectedUserAnswers;

    // количество ожидаемых ответов
    // необходимо для того, что бы задать минимум верных ответов
    // то есть, ожидаемых ответов может быть 10, и надо дать минимум три из них.
    // простой пример: "- Назовите сорта кофе, которые Вы знаете."
    @Column(name = "number_of_answers")
    private int requiredNumberOfAnswers;

    //реагировать ли на неверные ответы
    @Column(name = "react_on_incorrect_answers")
    private boolean reactOnIncorrectAnswerMessages;
    //сообщения, которыми реагировать. Будет показано случайно выбранное
    @Column(name = "incorrect_answers_react_mess", length = 1024)
    private String incorrectAnswersReactMessages;

    //реагировать ли на верные ответы
    @Column(name = "react_On_Correct_Answer_Messages")
    private boolean reactOnCorrectAnswerMessages;
    //сообщения, которыми реагировать. Будет показано случайно выбранное
    @Column(name = "correct_answers_react_mess", length = 1024)
    private String correctAnswersReactMessages;

    //геоточка
    @OneToOne(mappedBy = "nodeId", fetch = FetchType.EAGER)
    private GeoPoint geoPoint;

    //переключаться ли на новый пункт квеста при достижении основной геоточки
    @Column(name = "switch_on_reached")
    private boolean onReachedMainPointSwitchToNextNode;

    //время, через которое произойдет переключение
    @Column(name = "switch_time")
    private int pauseInSecBeforeSwitchingToTheNextNode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestNode questNode = (QuestNode) o;
        return id == questNode.id && requiredNumberOfAnswers == questNode.requiredNumberOfAnswers && reactOnIncorrectAnswerMessages == questNode.reactOnIncorrectAnswerMessages && reactOnCorrectAnswerMessages == questNode.reactOnCorrectAnswerMessages && onReachedMainPointSwitchToNextNode == questNode.onReachedMainPointSwitchToNextNode && pauseInSecBeforeSwitchingToTheNextNode == questNode.pauseInSecBeforeSwitchingToTheNextNode && Objects.equals(stageName, questNode.stageName) && Objects.equals(nextNode, questNode.nextNode) && Objects.equals(questId, questNode.questId) && Objects.equals(nodeMessages, questNode.nodeMessages) && Objects.equals(expectedUserAnswers, questNode.expectedUserAnswers) && Objects.equals(incorrectAnswersReactMessages, questNode.incorrectAnswersReactMessages) && Objects.equals(correctAnswersReactMessages, questNode.correctAnswersReactMessages) && Objects.equals(geoPoint, questNode.geoPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stageName, nextNode, questId, nodeMessages, expectedUserAnswers, requiredNumberOfAnswers, reactOnIncorrectAnswerMessages, incorrectAnswersReactMessages, reactOnCorrectAnswerMessages, correctAnswersReactMessages, geoPoint, onReachedMainPointSwitchToNextNode, pauseInSecBeforeSwitchingToTheNextNode);
    }

    @Override
    public String toString() {
        return "QuestNode{" +
                "id=" + id +
                '}';
    }

    /*    @Override
    public String toString() {
        return "QuestNode{" +
                "id=" + id +
                ", stageName='" + stageName + '\'' +
                ", expectedUserAnswers='" + Boolean.toString(expectedUserAnswers == null) + '\'' +
                ", requiredNumberOfAnswers=" + requiredNumberOfAnswers +
                ", reactOnIncorrectAnswerMessages=" + reactOnIncorrectAnswerMessages +
                ", incorrectAnswersReactMessages='" + Boolean.toString(incorrectAnswersReactMessages == null) + '\'' +
                ", reactOnCorrectAnswerMessages=" + reactOnCorrectAnswerMessages +
                ", correctAnswersReactMessages='" + Boolean.toString(correctAnswersReactMessages == null) + '\'' +
                ", geoPoint=" + Boolean.toString(geoPoint == null) +
                ", onReachedMainPointSwitchToNextNode=" + onReachedMainPointSwitchToNextNode +
                ", pauseInSecBeforeSwitchingToTheNextNode=" + pauseInSecBeforeSwitchingToTheNextNode +"\n\n"
                ;
    }*/
}

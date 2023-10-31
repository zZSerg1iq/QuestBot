package com.zinoviev.questbot.OLD.bot.entity.models.quest;

import com.zinoviev.sandbox.data.entity.quest.QuestNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BotQuestNode {

    private long id = 0;

    private String stageName = "Квест еще не стартовал";

    private long questId;


    private BotQuestNode nextNode;

    // основные сообщения квестовой точки, выводимые в начале
    private LinkedHashMap<String, Integer> nodeMessages;

    // список ожидаемых ответов
    private HashSet<String> expectedUserAnswers;

    // количество ожидаемых ответов
    // необходимо для того, что бы задать минимум верных ответов
    // то есть, ожидаемых ответов может быть 10, и надо дать минимум три из них.
    // простой пример: "- Назовите сорта кофе, которые Вы знаете."
    private int requiredNumberOfAnswers;

    //реагировать ли на неверные ответы
    private boolean reactOnIncorrectAnswerMessages;

    //сообщения, которыми реагировать. Будет показано случайно выбранное
    private List<String> incorrectAnswerReactMessages;

    //реагировать ли на верные ответы
    private boolean reactOnCorrectAnswerMessages;

    //сообщения, которыми реагировать. Будет показано случайно выбранное
    private List<String> correctAnswerReactMessages;

    //геоточка
    private BotGeoPoint geolocationPoint;

    //переключаться ли на новый пункт квеста при достижении основной геоточки
    private boolean onReachedMainPointSwitchToNextNode;

    //время, через которое произойдет переключение
    private int pauseInSecBeforeSwitchingToTheNextNode;

    public BotQuestNode(QuestNode node) {
        id = node.getId();
        stageName = node.getStageName();
        questId = node.getId();
        requiredNumberOfAnswers = node.getRequiredNumberOfAnswers();
        reactOnIncorrectAnswerMessages = node.isReactOnIncorrectAnswerMessages();
        reactOnCorrectAnswerMessages = node.isReactOnCorrectAnswerMessages();
        onReachedMainPointSwitchToNextNode = node.isOnReachedMainPointSwitchToNextNode();
        pauseInSecBeforeSwitchingToTheNextNode = node.getPauseInSecBeforeSwitchingToTheNextNode();

        if (node.getNodeMessages() != null) {
            List<String> mess = Arrays.stream(node.getNodeMessages().split("\n")).toList();
            nodeMessages = new LinkedHashMap<>();
            for (String s: mess ) {
                int x = Integer.parseInt(s.substring(s.lastIndexOf(" ")+1) );
                nodeMessages.put(s.substring(0, s.lastIndexOf(" ")), x);
            }
        }

        if (node.getExpectedUserAnswers() != null) {
            expectedUserAnswers = Arrays.stream(node.getExpectedUserAnswers().split("\n"))
                    .collect(Collectors.toCollection(HashSet::new));
        }

        if (node.getIncorrectAnswersReactMessages() != null) {
            incorrectAnswerReactMessages = Arrays.stream(node.getIncorrectAnswersReactMessages().split("\n")).toList();
        }

        if (node.getCorrectAnswersReactMessages() != null) {
            correctAnswerReactMessages = Arrays.stream(node.getCorrectAnswersReactMessages().split("\n")).toList();
        }

        if (node.getGeoPoint() != null) {
            geolocationPoint = new BotGeoPoint(node.getGeoPoint());
        }
    }



        @Override
    public String toString() {
        return "stageName='" + stageName + '\'' +
                ",\n\n questId=" + questId +
                ",\n\n nodeMessages=" + nodeMessages +
                ",\n\n expectedUserAnswers=" + expectedUserAnswers +
                ",\n\n requiredNumberOfAnswers=" + requiredNumberOfAnswers +
                ",\n\n reactOnIncorrectAnswerMessages=" + reactOnIncorrectAnswerMessages +
                ",\n\n incorrectAnswerReactMessages=" + incorrectAnswerReactMessages +
                ",\n\n reactOnCorrectAnswerMessages=" + reactOnCorrectAnswerMessages +
                ",\n\n correctAnswerReactMessages=" + correctAnswerReactMessages +
                ",\n\n geolocationPoint=" + geolocationPoint +
                ",\n\n onReachedMainPointSwitchToNextNode=" + onReachedMainPointSwitchToNextNode +
                ",\n\n pauseInSecBeforeSwitchingToTheNextNode=" + pauseInSecBeforeSwitchingToTheNextNode;
    }
}

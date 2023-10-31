package com.zinoviev.questbot.OLD.data.service.impl;

import com.zinoviev.sandbox.bot.entity.models.quest.BotGeoPoint;
import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;
import com.zinoviev.sandbox.bot.entity.models.quest.BotQuestNode;
import com.zinoviev.sandbox.data.entity.quest.GeoPoint;
import com.zinoviev.sandbox.data.entity.quest.Quest;
import com.zinoviev.sandbox.data.entity.quest.QuestNode;
import com.zinoviev.sandbox.data.entity.user.User;
import com.zinoviev.sandbox.data.repository.GeoPointRepository;
import com.zinoviev.sandbox.data.repository.QuestNodeRepository;
import com.zinoviev.sandbox.data.repository.QuestRepository;
import com.zinoviev.sandbox.data.repository.UserRepository;
import com.zinoviev.sandbox.data.service.QuestRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class QuestRepositoryServiceImpl implements QuestRepositoryService {


    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final QuestNodeRepository questNodeRepository;
    private final GeoPointRepository geoPointRepository;

    private final StringBuilder stringBuilder = new StringBuilder();

    @Autowired
    public QuestRepositoryServiceImpl(QuestRepository questRepository, UserRepository userRepository, QuestNodeRepository questNodeRepository, GeoPointRepository geoPointRepository) {
        this.questRepository = questRepository;
        this.userRepository = userRepository;
        this.questNodeRepository = questNodeRepository;
        this.geoPointRepository = geoPointRepository;
    }


    @Override
    public BotQuest getQuestById(long questId) {
        return new BotQuest(questRepository.findById(questId).orElseGet(null));
    }

    @Override
    public List<BotQuest> getUserQuestList(Long id) {
        List<Quest> questList = questRepository.findAllByAuthor_Id(id);

        List<BotQuest> botUserQuests = new ArrayList<>();
        if (questList.size() > 0){
            questList.forEach(quest -> botUserQuests.add(new BotQuest(quest)));
        }
        return botUserQuests;
    }

    @Override
    public void saveQuestList(List<BotQuest> quests) {
        if (quests != null && quests.size() > 0 ) {
            User user = userRepository.getUserByTelegramId(quests.get(0).getAuthor());

            for (var botQuest : quests) {
                Quest temp = new Quest();

                temp.setId(botQuest.getId() == -1L ? 0 :botQuest.getId());
                temp.setCost(botQuest.getCost());
                temp.setSharedType(botQuest.getShared());
                temp.setQuestName(botQuest.getName());
                temp.setFirstPlayerCompletesTheQuest(botQuest.isFirstPlayerCompletesTheQuest());
                temp.setAuthor(user);
                Quest quest = questRepository.save(temp);




                //блок сохранения нод квеста
                List<BotQuestNode> botQuestNodes = botQuest.getTheQuestQueue();
                List<QuestNode> questNodes = new ArrayList<>();

                for (var botNode: botQuestNodes) {
                    QuestNode questNode = new QuestNode();
                    questNode.setQuestId(quest);
                    questNode.setStageName(botNode.getStageName());

                    questNode.setNodeMessages(getNodeMessages(botNode.getNodeMessages()));
                    questNode.setExpectedUserAnswers(getExpectedUserAnswers(botNode.getExpectedUserAnswers()));
                    questNode.setRequiredNumberOfAnswers(botNode.getRequiredNumberOfAnswers());

                    if (botNode.isReactOnIncorrectAnswerMessages()) {
                        questNode.setReactOnIncorrectAnswerMessages(true);
                        if (botNode.getIncorrectAnswerReactMessages() != null) {
                            questNode.setIncorrectAnswersReactMessages(getIncorrectAnswerReactMessages(botNode.getIncorrectAnswerReactMessages()));
                        }
                    } else {
                        questNode.setReactOnIncorrectAnswerMessages(false);
                    }

                    if (botNode.isReactOnCorrectAnswerMessages()){
                        questNode.setReactOnCorrectAnswerMessages(true);
                        if (botNode.getCorrectAnswerReactMessages() != null) {
                            questNode.setCorrectAnswersReactMessages(getIncorrectAnswerReactMessages(botNode.getCorrectAnswerReactMessages()));
                        }
                    }else {
                        questNode.setReactOnCorrectAnswerMessages(false);
                    }

                    questNode.setOnReachedMainPointSwitchToNextNode(botNode.isOnReachedMainPointSwitchToNextNode());
                    questNode.setPauseInSecBeforeSwitchingToTheNextNode(botNode.getPauseInSecBeforeSwitchingToTheNextNode());

                    questNodeRepository.save(questNode);
                    // нужно сохранить текущую ноду для того,
                    // что бы геоточка могла принять айди, который появится после сохранения

                    if (botNode.getGeolocationPoint() != null) {

                        questNode.setGeoPoint(getGeoPoint(botNode.getGeolocationPoint(), questNode));
                        questNodeRepository.save(questNode);
                    }

                    questNodes.add(questNode);

                    if (quest.getStartNode() == null) {
                        quest.setStartNode(questNodes);
                        questRepository.save(quest);
                        // устанавливаем первую ноду квеста
                        // и снова сохраняем его
                    }

                }

                // устанавливаем nextNode для всех нодов текущего квеста
                for (int i = 0; i < questNodes.size()-1; i++) {
                    if (i+1 < questNodes.size()) {
                        questNodes.get(i).setNextNode(questNodes.get(i+1));
                    }
                }
                // и сохраняем все ноды
                questNodeRepository.saveAll(questNodes);
            }
         }
    }

    private String getNodeMessages(LinkedHashMap<String, Integer> messList){
        stringBuilder.setLength(0);
        for (var message: messList.entrySet() ) {
            stringBuilder.append(message.getKey()).append(message.getValue()).append("\n");
        }
        return stringBuilder.toString();
    }

    private String getExpectedUserAnswers(HashSet<String> answers){
        stringBuilder.setLength(0);
        if (answers != null){
            for (var answer: answers) {
                stringBuilder.append(answer).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private String getIncorrectAnswerReactMessages(List<String> incorrectAnswers) {
        stringBuilder.setLength(0);
        for (var answer : incorrectAnswers) {
            stringBuilder.append(answer).append("\n");
        }
        return stringBuilder.toString();
    }

    private GeoPoint getGeoPoint(BotGeoPoint botGeoPoint, QuestNode questNode) {
        stringBuilder.setLength(0);
        GeoPoint geoPoint = new GeoPoint();
        geoPoint.setNodeId(questNode);
        geoPoint.setLatitude(botGeoPoint.getLatitude());
        geoPoint.setLongitude(botGeoPoint.getLongitude());

        if (botGeoPoint.getGeoPointOuterRadius() != -1) {
            geoPoint.setGeoPointOuterRadius(botGeoPoint.getGeoPointOuterRadius());

            var outerRadiusMessages = botGeoPoint.getOuterRadiusReachedMessages();
            if (outerRadiusMessages != null) {
                stringBuilder.setLength(0);
                for (var mess : outerRadiusMessages.entrySet()) {
                    stringBuilder.append(mess.getKey()).append(mess.getValue()).append("\n");
                }
                geoPoint.setGeoPointOuterRadiusReachedMessages(stringBuilder.toString());
            }
        }


        if (botGeoPoint.getGeoPointMeddleRadius() != -1) {
            geoPoint.setGeoPointMeddleRadius(botGeoPoint.getGeoPointMeddleRadius());
            var middleRadiusMessages = botGeoPoint.getMiddleRadiusReachedMessages();
            if (middleRadiusMessages != null) {
                stringBuilder.setLength(0);
                for (var mess : middleRadiusMessages.entrySet()) {
                    stringBuilder.append(mess.getKey()).append(mess.getValue()).append("\n");
                }
                geoPoint.setGeoPointMeddleRadiusReachedMessages(stringBuilder.toString());
            }
        }


        geoPoint.setGeoPointRadius(botGeoPoint.getGeoPointRadius());
        var onPointRadiusMessages = botGeoPoint.getOnPointMessages();
        if (onPointRadiusMessages != null) {
            stringBuilder.setLength(0);
            for (var mess : onPointRadiusMessages.entrySet()) {
                stringBuilder.append(mess.getKey()).append(mess.getValue()).append("\n");
            }

            geoPoint.setOnPointMessages(stringBuilder.toString());
        }
        geoPointRepository.save(geoPoint);


        return geoPoint;
    }

    @Override
    public void saveQuest(BotQuest botQuest) {
        User user = userRepository.findById(botQuest.getAuthor()).orElse(new User());

        Quest quest = new Quest();
        quest.setId(botQuest.getId());
        quest.setCost(botQuest.getCost());
        quest.setFirstPlayerCompletesTheQuest(botQuest.isFirstPlayerCompletesTheQuest());
        quest.setSharedType(botQuest.getShared());
        quest.setQuestName(botQuest.getName());
        quest.setAuthor(user);

        questRepository.save(quest);
    }
}

package com.zinoviev.questbot.OLD.quest_file_validator.data.qustParser;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;

import java.util.Queue;

public class QuestParser {

    private final DataLoader dataLoader;
    private final Queue<String> questMainMessageQueue;


    public QuestParser(String filename) {
        dataLoader = new DataLoader(filename);
        questMainMessageQueue = dataLoader.getQuestSteps("quest_step_list");
    }


    public BotQuest loadAndParseQuest() {
        BotQuest quest = new BotQuest();
       /* LinkedList<BotQuestNode> questStagesQueue = new LinkedList<>();

        while (!questMainMessageQueue.isEmpty()) {
            String currentQuestPart = questMainMessageQueue.poll();

            System.out.println(currentQuestPart);

            var questPartMap = dataLoader.getQuestNextStep(currentQuestPart);
            var answers = questPartMap.get("answers");

            BotQuestNode botQuestNode = new BotQuestNode();

            botQuestNode.setStageName(currentQuestPart);

            botQuestNode.setQuestMessages(questPartMap.get("messages"));
            botQuestNode.setShowIncorrectAnswerMessages(questPartMap.get("answers").get("showReplyMessages") == 1);
            botQuestNode.setRequiredNumberOfAnswers(questPartMap.get("answers").get("answersRequiredNumber"));

            answers.entrySet().removeIf(stringIntegerEntry -> stringIntegerEntry.getKey().equals("showReplyMessages"));
            answers.entrySet().removeIf(stringIntegerEntry -> stringIntegerEntry.getKey().equals("answersRequiredNumber"));


            Iterator<Map.Entry<String, Integer>> iterator = answers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();

                //Если наден тег геолокации
                if (entry.getKey().contains("geoloc")) {
                    String[] temp = dataLoader.getGeoCoordinates(entry.getKey()).split("\\+");

                    var tempMap = new HashMap<Integer, String>();
                    for (int j = 0; j < temp.length; j++) {
                        tempMap.put(j, temp[j]);
                    }
                    botQuestNode.setGeolocationPoints(tempMap);

                    var geolocMessages = new HashMap<String, LinkedHashMap<String, Integer>>();
                    geolocMessages.put("on_point", dataLoader.getOnPointMessages(entry.getKey() + "_on_point_text"));
                    geolocMessages.put("inner_radius", dataLoader.getOnPointMessages(entry.getKey() + "_inner_radius_text"));
                    geolocMessages.put("outer_radius", dataLoader.getOnPointMessages(entry.getKey() + "_outer_radius_text"));
                    geolocMessages.put("out_of_range", dataLoader.getOnPointMessages(entry.getKey() + "_out_of_range_text"));
                    geolocMessages.put("hasty_answer", dataLoader.getOnPointMessages(entry.getKey() + "_hasty_answer"));
                    botQuestNode.setGeoLocationMessages(geolocMessages);

                    iterator.remove();

                    botQuestNode.setOnReachedSwitchToNextStage(dataLoader.getBoolean(entry.getKey() +"_on_reached_switch_to_next_quest_stage"));
                    botQuestNode.setOnReachedSwitchToNextStageTime(dataLoader.getInt(entry.getKey() +"_switching_time"));

                    botQuestNode.setGeoOnPointRadius(
                            dataLoader.getInt(entry.getKey() + "_on_point_radius_range") != 1 ?
                                    dataLoader.getInt(entry.getKey() + "_on_point_radius_range") :
                                    20);
                    botQuestNode.setGeoPointInnerRadius(
                            dataLoader.getInt(entry.getKey() + "_inner_radius_range") != 1 ?
                                    dataLoader.getInt(entry.getKey() + "_inner_radius_range") :
                                    80);
                    botQuestNode.setGeoPointOuterRadius(
                            dataLoader.getInt(entry.getKey() + "_outer_radius_range") != 1 ?
                                    dataLoader.getInt(entry.getKey() + "_outer_radius_range") :
                                    200);
                }
            }


            botQuestNode.setUserAnswers(new HashSet<>(answers.keySet()));
            questStagesQueue.add(botQuestNode);
        }
        quest.setTheQuestQueue(questStagesQueue);*/

        return quest;
    }






}

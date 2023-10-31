package com.zinoviev.questbot.OLD.bot.quest_management;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuestNode;
import com.zinoviev.sandbox.bot.entity.models.running_quest.RunningBotQuest;
import com.zinoviev.sandbox.bot.entity.models.running_quest.RunningBotQuestMember;
import com.zinoviev.sandbox.bot.entity.models.user.BotUser;
import com.zinoviev.sandbox.data.service.RunningQuestRepositoryService;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RunningQuestManager {

    private static class RunningQuestInstanceHolder {
        private static final RunningQuestManager questManager = new RunningQuestManager();
    }

    private RunningQuestRepositoryService runningQuestService;

    private final ConcurrentHashMap<String, RunningBotQuest> questsCache;
    private final ConcurrentHashMap<String, List<RunningBotQuestMember>> questsMembersCache;

    private RunningQuestManager() {
        questsMembersCache = new ConcurrentHashMap<>();
        questsCache = new ConcurrentHashMap<>();
   }
    public static RunningQuestManager getInstance(){
        return RunningQuestInstanceHolder.questManager;
    }




    public void setServices(RunningQuestRepositoryService runningQuestService) {
        this.runningQuestService = runningQuestService;
    }
    public void fillTheCacheOfRunningQuests(){
        var runningQuests = runningQuestService.getAll();

        if (runningQuests.size() > 0){
            for (var quest: runningQuests) {
                questsCache.put(quest.getQuestPublicLink(), quest);

                quest.getMembers().forEach(
                        member -> {
                            member.setExpectedUserAnswers(
                                    quest.getCurrentNode().getExpectedUserAnswers());
                            member.setAnswerCount(quest.getCurrentNode().getRequiredNumberOfAnswers());
                        }
                );

                questsMembersCache.put(quest.getQuestPublicLink(), quest.getMembers());
            }
        }
    }
    private void setNewRequirements(String questLink, BotQuestNode node){
        questsMembersCache.get(questLink).forEach(
                member -> {
                    member.setExpectedUserAnswers(
                            node.getExpectedUserAnswers());
                    member.setAnswerCount(node.getRequiredNumberOfAnswers());
                }
        );
    }





    public boolean isQuestAlreadyRunning(String publicLink){
        return questsCache.get(publicLink) != null;
    }
    public void addRunningQuest(String publicLink, RunningBotQuest runningBotQuest){
        questsCache.put(publicLink, runningBotQuest);
    }
    public LinkedHashMap<String, Integer> getCurrentStepMessages(String publicLink){
        if (questsCache.get(publicLink) != null) {
            return questsCache.get(publicLink).getCurrentNode().getNodeMessages();
        }
        var error = new LinkedHashMap<String, Integer>();
        error.put("Квест еще не стартовал", 0);

        return error;
    }





    public boolean isBotUserAlreadyInQuest(String publicLink, BotUser botUser){
        if (questsMembersCache.get(publicLink) != null) {
            return questsMembersCache.get(publicLink).stream().filter(member -> member.getUser().equals(botUser)).findFirst().orElse(null) != null;
        } else return false;
    }
    public LinkedHashMap<String, Integer> addQuestMember(BotUser botUser){
        if (isBotUserAlreadyInQuest(botUser.getQuestPublicLink(), botUser)){
            questsMembersCache.get(botUser.getQuestPublicLink()).forEach(runningBotQuestMember -> {
                if (runningBotQuestMember.getUser().equals(botUser)){
                    runningBotQuestMember.setActive(true);
                }
            });
        } else {
            questsMembersCache.computeIfAbsent(botUser.getQuestPublicLink(), k -> new ArrayList<>());
            RunningBotQuestMember newMember = new RunningBotQuestMember();
            newMember.setUser(botUser);
            newMember.setActive(true);
            questsMembersCache.get(botUser.getQuestPublicLink()).add(newMember);
        }

        questsMembersCache.get(botUser.getQuestPublicLink())
                .stream()
                .filter(member -> Objects.equals(member.getUser().getId(), botUser.getId()))
                .findFirst()
                .ifPresent(member -> {
                    member.setExpectedUserAnswers(
                            questsCache.get(
                                    botUser.getQuestPublicLink()).getCurrentNode().getExpectedUserAnswers()
                    );
                    member.setAnswerCount(
                            questsCache.get(
                                    botUser.getQuestPublicLink()).getCurrentNode().getRequiredNumberOfAnswers()
                    );
                });

        return getCurrentStepMessages(botUser.getQuestPublicLink());
    }
    public void banishMember(BotUser botUser){
        questsMembersCache.get(botUser.getQuestPublicLink()).forEach(runningBotQuestMember -> {
            if (runningBotQuestMember.getUser().equals(botUser)){
                runningBotQuestMember.setActive(false);
            }
        });
    }
    public RunningBotQuestMember getQuestMember(String questPublicLink, Long id) {
        if (questsMembersCache.get(questPublicLink) != null) {
            return questsMembersCache.get(questPublicLink).stream()
                    .filter(member -> member.getUser().getId().equals(id)).findFirst().get();
        }
        return null;
    }



    public List<BotUser> getQuestUserList(String publicLink){
        return questsMembersCache.get(publicLink).stream().map(RunningBotQuestMember::getUser).toList();
    }
    public List<RunningBotQuestMember> getQuestMembersList(String publicLink){
        return questsMembersCache.get(publicLink);
    }




    public boolean removeQuestByPublicLink(String publicLink, long userId){
        if (questsMembersCache.get(publicLink).size() == 0) {
            questsCache.remove(publicLink);
            questsMembersCache.remove(publicLink);
            return false;
        }

        questsMembersCache.get(publicLink).removeIf(runningBotQuestMember -> runningBotQuestMember.getUser().getId() == userId);
        return true;
    }




    public QuestResponseModel playerUpdateHandler(String publicLink, long userId, Update update) {
        System.out.println("НОДА НОМЕР "+questsCache.get(publicLink).getCurrentNode().getId());
        BotQuestNode questNode = questsCache.get(publicLink).getCurrentNode();
        var member = getQuestMember(publicLink, userId);

        System.out.println("===========================================Требования====================================== ");
        if (questNode.getRequiredNumberOfAnswers() > 0){
            System.out.println("Дать "+questNode.getRequiredNumberOfAnswers()+" ответов: ");
            if (questNode.getGeolocationPoint() != null) {
                System.out.print("А так же ");
            }
        }
        if (questNode.getGeolocationPoint() != null) {
            System.out.println("достигнуть геоточки: ");

            System.out.println(
            questNode.getGeolocationPoint().getGeoPointRadius() +" / " +
            questNode.getGeolocationPoint().getGeoPointMeddleRadius() + " / " +
            questNode.getGeolocationPoint().getGeoPointOuterRadius()
            );
        }
        System.out.println("========================================================================================== ");


        QuestResponseModel responseModel = new QuestResponseModel();

        if (update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            //обработчик текстовой составляющей
            responseModel.setTextMessageResponse(processTextMessage(message, member, questNode));
        }



        if (update.getMessage().hasLocation() && questNode.getGeolocationPoint() != null) {
            Location location = update.getMessage().getLocation();
            //обработчик геопозиции

            //сравнить с мембером и если есть результат, отослать сообщения, которые еще не получал
            MemberGeoPointStatus geoPointStatus = processLocation(location, member, questNode);
            if (geoPointStatus.getRadiusMessages() != null) {
                responseModel.setGeoPositionResponse(geoPointStatus.getRadiusMessages());
            }

            responseModel.setGeoPositionDistanceMessage(geoPointStatus.getDistanceMessage());
        }



        boolean lastNode = false;
        if (questNode.getRequiredNumberOfAnswers() > 0 & questNode.getGeolocationPoint() != null){
            System.out.println("ans + point");
            if (member.getAnswerCount() <= 0 & member.isPointReached()){
                lastNode = memberWinningAStage(member, questNode, publicLink);
            }

        } else if (questNode.getRequiredNumberOfAnswers() > 0 & questNode.getGeolocationPoint() == null){
            System.out.println("ans");
            if (member.getAnswerCount() <= 0){
                lastNode = memberWinningAStage(member, questNode, publicLink);
            }

        } else if (questNode.getGeolocationPoint() != null){
            System.out.println("point");
            if (member.isPointReached()){
                lastNode = memberWinningAStage(member, questNode, publicLink);
            }
        }

        if (lastNode){
            responseModel.setTheLastNode(true);
        }

        return responseModel;
    }




    private MemberGeoPointStatus processLocation(Location location, RunningBotQuestMember member, BotQuestNode questNode) {
        MemberGeoPointStatus memberGeoPointStatus = null;

        if (questNode.getGeolocationPoint() != null) {
            //если геоточка есть
              memberGeoPointStatus =
                    new GeoPositionHandler(questNode.getGeolocationPoint(), member, location).check();
        }

        return memberGeoPointStatus;
    }

    private LinkedHashMap<String, Integer> processTextMessage(String message, RunningBotQuestMember member, BotQuestNode questNode) {
        var result = new LinkedHashMap<String, Integer>();

        var answers = questNode.getExpectedUserAnswers();

        if (questNode.isReactOnCorrectAnswerMessages()){
            var correctReact = questNode.getCorrectAnswerReactMessages();
        }
        if (questNode.isReactOnIncorrectAnswerMessages()){
            var incorrectMess = questNode.getCorrectAnswerReactMessages();
        }

        if (answers != null){
            //System.out.println("Дано ответов: "+member.getAnswerCount());
           // System.out.println("----------------------------------------------");

            int before =  member.getExpectedUserAnswers().size();
            member.getExpectedUserAnswers()
                    .removeIf(s ->
                            s.toLowerCase().contains(message.toLowerCase())
                                    || message.toLowerCase().contains(s.toLowerCase())
                   );

           // System.out.println("---------------- Сами ответы --------------");
           // member.getExpectedUserAnswers().forEach(System.out::println);

            if (member.getExpectedUserAnswers().size() != before){
             //  System.out.println("------------------------ YES -------------------------");
                member.setAnswerCount(member.getAnswerCount() - (before - member.getExpectedUserAnswers().size()));
            }
       }

        return result;
    }


    private boolean memberWinningAStage(RunningBotQuestMember member, BotQuestNode questNode, String publicLink) {
        boolean result = true;

        System.out.println("---------------------------- ИГРОК ЗАРАБАТЫВАЕТ 10 ОЧКОВ -------------------------------");
        member.getUser().setUserPoints(member.getUser().getUserPoints() + 10);

        System.out.println("----------------------------СЛЕДУЮЩАЯ ТОЧКА КВЕСТА -------------------------------");
        if (questNode.getNextNode() != null) {
            questsCache.get(publicLink).setCurrentNode(questNode.getNextNode());
            setNewRequirements(publicLink, questsCache.get(publicLink).getCurrentNode());
            result = false;
        }

        runningQuestService.saveRunningQuest(questsCache.get(publicLink));
        System.out.println("НОДА НОМЕР "+questsCache.get(publicLink).getCurrentNode().getId());

        return result;
    }


    @Override
    public String toString() {
        return "RunningQuestManager{" +
                ", questsCache.size=" + questsCache.size() +
                ", all quest members count=" + questsMembersCache.size() +
                '}';
    }
}

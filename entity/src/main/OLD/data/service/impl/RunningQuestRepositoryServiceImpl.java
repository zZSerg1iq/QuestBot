package com.zinoviev.sandbox.data.service.impl;

import com.zinoviev.sandbox.bot.entity.models.running_quest.RunningBotQuestMember;
import com.zinoviev.sandbox.bot.entity.models.running_quest.RunningBotQuest;
import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMuteUserState;
import com.zinoviev.sandbox.data.entity.quest.Quest;
import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMembers;
import com.zinoviev.sandbox.data.entity.running_quest.RunningQuest;
import com.zinoviev.sandbox.data.entity.user.User;
import com.zinoviev.sandbox.data.repository.QuestRepository;
import com.zinoviev.sandbox.data.repository.RunningQuestRepository;
import com.zinoviev.sandbox.data.repository.UserRepository;
import com.zinoviev.sandbox.data.service.RunningQuestRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class RunningQuestRepositoryServiceImpl implements RunningQuestRepositoryService{

    private final RunningQuestRepository runningQuestRepository;
    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final QuestNodeRepositoryServiceImpl questNodeRepositoryImpl;

    @Autowired
    public RunningQuestRepositoryServiceImpl(RunningQuestRepository runningQuestRepository, QuestRepository questRepository, UserRepository userRepository, QuestNodeRepositoryServiceImpl questNodeRepositoryImpl) {
        this.runningQuestRepository = runningQuestRepository;
        this.questRepository = questRepository;
        this.userRepository = userRepository;
        this.questNodeRepositoryImpl = questNodeRepositoryImpl;
    }

    @Override
    public boolean isRunningQuestExist(String link) {
        return runningQuestRepository.findByQuestPublicLink(link) != null;
    }

    @Override
    public void saveRunningQuest(RunningBotQuest runningQuest) {
        RunningQuest resultQuest = runningQuestRepository.findByQuestPublicLink(runningQuest.getQuestPublicLink());
        System.out.println(runningQuest.getCurrentNode().getId());


        if (resultQuest == null){
            resultQuest = new RunningQuest();
       }

        resultQuest.setQuestId(questRepository.findById(runningQuest.getQuestId()).orElse(new Quest()));
        resultQuest.setQuestPublicLink(runningQuest.getQuestPublicLink());
        resultQuest.setCurrentQuestNode(resultQuest.getCurrentQuestNode());
        resultQuest.setOwnerId(userRepository.findById(runningQuest.getOwnerId()).orElse(new User()));
        resultQuest.setCurrentQuestNode(questNodeRepositoryImpl.findQuestNodeById(runningQuest.getCurrentNode().getId()));
        runningQuestRepository.save(resultQuest);
    }

    @Override
    public RunningBotQuest getRunningQuestByPublicLink(String link) {
        return new RunningBotQuest(runningQuestRepository.findByQuestPublicLink(link));
    }

    @Override
    public void saveQuestMembers(List<RunningBotQuestMember> botQuestMembers, String questPublicLink) {
        RunningQuest resultQuest = runningQuestRepository.findByQuestPublicLink(questPublicLink);
        var members = resultQuest.getMembers();

        if (members == null){
            members = new ArrayList<>();
        }
        for (var member: botQuestMembers) {
            //if present - get, else - new
            RunningQuestMembers dataMember = members.stream()
                    .filter(m -> Objects.equals(m.getUser().getId(), member.getUser().getId()))
                    .findFirst()
                    .orElse(new RunningQuestMembers());

            dataMember.setActive(member.isActive());
            User user = userRepository.getUserByTelegramId(member.getUser().getTelegramId());
            dataMember.setUser(user);
            dataMember.setRunningQuest(resultQuest);
            dataMember.setOutRadiusReached(member.isOutRadiusReached());
           // dataMember.setOutRadiusMessReceived(member.isOutRadiusMessReceived());
            dataMember.setMidRadiusReached(member.isMidRadiusReached());
            //dataMember.setMidRadiusMessReceived(member.isMidRadiusMessReceived());
            dataMember.setPointReached(member.isPointReached());
           // dataMember.setPointRadiusMessReceived(member.isPointRadiusMessReceived());
            dataMember.setLastDistanceFromThePoint(member.getLastDistanceFromThePoint());

            //mute
            if (member.getMuteUserState() != null) {
                RunningQuestMuteUserState mute = new RunningQuestMuteUserState();
                mute.setMuteStartMessageTime(member.getMuteUserState().getMuteStartMessageTime());
                mute.setMuteEndMessageTime(member.getMuteUserState().getMuteEndMessageTime());
                dataMember.setRunningQuestMutedUserState(mute);
            } else {
                dataMember.setRunningQuestMutedUserState(null);
            }

            if (dataMember.getId() == -1) {
                members.add(dataMember);
            }
        }

        resultQuest.setMembers(members);
        runningQuestRepository.save(resultQuest);
    }

    @Override
    public List<RunningBotQuest> getAll() {
        List<RunningQuest> runningQuestsList = runningQuestRepository.findAll();
        List<RunningBotQuest> runningBotQuestList = new ArrayList<>();

        if (runningQuestsList.size() > 0) {
            for (var dataQuest : runningQuestsList) {
                RunningBotQuest runningBotQuest = new RunningBotQuest(dataQuest);
                runningBotQuest.setCurrentNode(
                        questNodeRepositoryImpl.findByQuest_IdAndNodeId(dataQuest.getId(), dataQuest.getCurrentQuestNode().getId()));
                runningBotQuestList.add(runningBotQuest);
            }
        }

        return runningBotQuestList;
    }

    @Override
    public void deleteByQuestPublicLink(String publicLink) {
        runningQuestRepository.deleteByQuestPublicLink(publicLink);
    }


}

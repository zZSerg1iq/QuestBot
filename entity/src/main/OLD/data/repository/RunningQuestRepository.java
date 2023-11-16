package com.zinoviev.sandbox.data.repository;

import com.zinoviev.sandbox.data.entity.running_quest.RunningQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningQuestRepository  extends JpaRepository<RunningQuest, Long> {

    RunningQuest findByQuestPublicLink(String link);

    void deleteByQuestPublicLink(String publicLink);


}

package com.zinoviev.data.repository;

import com.zinoviev.entity.data.runnning.quest.ActiveQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveQuestRepository extends JpaRepository<ActiveQuest, Long> {

    ActiveQuest findActiveQuestByInviteLink(String inviteLink);
}

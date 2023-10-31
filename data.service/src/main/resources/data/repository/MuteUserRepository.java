package com.zinoviev.questbot.OLD.data.repository;

import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMuteUserState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuteUserRepository extends JpaRepository<RunningQuestMuteUserState, Long> {

}

package com.zinoviev.data.repository;

import com.zinoviev.entity.data.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}

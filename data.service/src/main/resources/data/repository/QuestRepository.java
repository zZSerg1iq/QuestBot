package com.zinoviev.questbot.OLD.data.repository;


import com.zinoviev.sandbox.data.entity.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {

    List<Quest> findAllByAuthor_Id(Long id);



}
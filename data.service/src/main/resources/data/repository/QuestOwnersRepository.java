package com.zinoviev.questbot.OLD.data.repository;


import com.zinoviev.sandbox.data.entity.quest.QuestOwners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestOwnersRepository extends JpaRepository<QuestOwners, Long> {

    List<QuestOwners> findAllByOwner_Id(long id);

}
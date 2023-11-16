package com.zinoviev.sandbox.data.repository;


import com.zinoviev.sandbox.data.entity.quest.Quest;
import com.zinoviev.sandbox.data.entity.quest.QuestOwners;
import com.zinoviev.sandbox.data.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestOwnersRepository extends JpaRepository<QuestOwners, Long> {

    List<QuestOwners> findAllByOwner_Id(long id);

}
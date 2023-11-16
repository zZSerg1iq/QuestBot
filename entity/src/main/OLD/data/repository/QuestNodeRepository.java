package com.zinoviev.sandbox.data.repository;

import com.zinoviev.sandbox.data.entity.quest.QuestNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestNodeRepository extends JpaRepository<QuestNode, Long> {

    List<QuestNode> findByQuestId_Id(long id);

    QuestNode findById(long nodeId);

}

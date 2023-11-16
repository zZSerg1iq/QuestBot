package com.zinoviev.sandbox.data.repository;

import com.zinoviev.sandbox.data.entity.running_quest.RunningQuestMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestRepository extends JpaRepository<RunningQuestMembers, Long> {
}

package com.zinoviev.data.repository;

import com.zinoviev.entity.data.runnning.quest.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}

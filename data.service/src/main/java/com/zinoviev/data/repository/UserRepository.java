package com.zinoviev.data.repository;

import com.zinoviev.entity.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByTelegramId(long userId);



}
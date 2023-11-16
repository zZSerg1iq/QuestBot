package com.zinoviev.data.repository;

import com.zinoviev.entity.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByTelegramId(long userId);

    User getUserByAvatarName(String name);

}
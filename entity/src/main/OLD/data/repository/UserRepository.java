package com.zinoviev.sandbox.data.repository;

import com.zinoviev.sandbox.data.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByTelegramId(long id);

    User getUserByAvatarName(String name);


}
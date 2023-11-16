package com.zinoviev.sandbox.data.repository;

import com.zinoviev.sandbox.data.entity.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountsRepository extends JpaRepository<UserAccount, Long> {
}

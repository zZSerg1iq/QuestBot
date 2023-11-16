package com.zinoviev.entity.data.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_accounts")
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "balance")
    private double balance;

    @Column(name = "active")
    private boolean active;

}

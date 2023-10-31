package com.zinoviev.entity.data;

import com.zinoviev.entity.enums.Role;
import com.zinoviev.entity.enums.SignInStatus;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "telegram_id", unique = true)
    private Long telegramId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "uer_name")
    private String userName;

    @Column(name = "avatar_name")
    private String avatarName;

    @Column(name = "role")
    private Role role;

    @Column(name = "sign_in_status")
    private SignInStatus signInStatus;

    @OneToOne(mappedBy = "user")
    private Statistics statistics;

}

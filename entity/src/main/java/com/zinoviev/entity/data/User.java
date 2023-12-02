package com.zinoviev.entity.data;

import com.zinoviev.entity.data.runnning.quest.Player;
import com.zinoviev.entity.enums.SignInStatus;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "telegram_id", unique = true)
    private long telegramId;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "telegram_name")
    private String telegramName;

    @Column(name = "sign_in_status")
    private SignInStatus signInStatus;

    @OneToOne(mappedBy = "userId")
    private Player player;

}

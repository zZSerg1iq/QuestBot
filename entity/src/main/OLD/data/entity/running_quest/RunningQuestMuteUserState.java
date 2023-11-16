package com.zinoviev.sandbox.data.entity.running_quest;

import com.zinoviev.sandbox.data.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "muted_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RunningQuestMuteUserState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "mute_start_time")
    private int muteStartMessageTime = 0;

    @Column(name = "mute_end_time")
    private int muteEndMessageTime = 0;

    @Override
    public String toString() {
        return "UserMute{" +
                "muteStartMessageTime=" + muteStartMessageTime +
                ", muteEndMessageTime=" + muteEndMessageTime +
                '}';
    }
}

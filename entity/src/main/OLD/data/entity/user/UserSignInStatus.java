package com.zinoviev.sandbox.data.entity.user;

import com.zinoviev.sandbox.bot.entity.SignInStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_sign_in_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSignInStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "sign_in_status")
    @Enumerated(EnumType.STRING)
    private SignInStatus status;

    public UserSignInStatus(User user, SignInStatus status) {
        this.user = user;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserSignInStatus{" +
                "status=" + status +
                '}';
    }
}
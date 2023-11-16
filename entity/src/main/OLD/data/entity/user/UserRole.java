package com.zinoviev.sandbox.data.entity.user;

import com.zinoviev.sandbox.bot.entity.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }


    @Override
    public String toString() {
        return "UserRole{" +
                "role=" + role +
                '}';
    }
}
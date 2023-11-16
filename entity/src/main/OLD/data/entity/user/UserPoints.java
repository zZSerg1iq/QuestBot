package com.zinoviev.sandbox.data.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_points")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "points")
    private int points;

    public UserPoints(User user, int points) {
        this.user = user;
        this.points = points;
    }

    @Override
    public String toString() {
        return "UserPoints{" +
                "points=" + points +
                '}';
    }
}
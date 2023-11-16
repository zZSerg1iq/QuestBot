package com.zinoviev.sandbox.data.entity.user;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    public UserAccount(User user, boolean active, double balance) {
        this.user = user;
        this.active = active;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "balance=" + balance +
                ", active=" + active +
                '}';
    }
}

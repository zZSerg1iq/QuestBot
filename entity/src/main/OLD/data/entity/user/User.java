package com.zinoviev.sandbox.data.entity.user;

import com.zinoviev.sandbox.data.entity.quest.Quest;
import com.zinoviev.sandbox.data.entity.quest.QuestOwners;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "telegram_id", unique = true)
    private Long telegramId;

    @Column(name = "telegram_name")
    private String telegramName;

    @Column(name = "avatar_name")
    private String avatarName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private UserRole userRole;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private UserSignInStatus userSignInStatus;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private UserPoints userPoints;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "author")
    private List<Quest> userQuests;

    @OneToMany(mappedBy = "owner")
    private List<QuestOwners> userPurchasedQuests;

    @Column(name = "quest_public_Link")
    private String questPublicLink;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", telegramName='" + telegramName + '\'' +
                ", avatarName='" + avatarName + '\'' +
                ", userRole=" + userRole.getRole().getRoleName() +
                ", userSignInStatus=" + userSignInStatus.getStatus() +
                ", userPoints=" + userPoints.getPoints() +
                ", userQuests=" + userQuests +
                ", questPublicLink='" + questPublicLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && telegramId.equals(user.telegramId) && Objects.equals(telegramName, user.telegramName) && Objects.equals(avatarName, user.avatarName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramId, telegramName, avatarName);
    }
}
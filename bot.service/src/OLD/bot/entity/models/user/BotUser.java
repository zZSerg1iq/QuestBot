package com.zinoviev.questbot.OLD.bot.entity.models.user;

import com.zinoviev.sandbox.bot.entity.Role;
import com.zinoviev.sandbox.bot.entity.SignInStatus;
import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;
import com.zinoviev.sandbox.data.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class  BotUser {

    private Long id = 0L;

    private Long telegramId = 0L;

    private String telegramName;

    private String avatarName;

    private Role role;

    private SignInStatus userStatus;

    private int userPoints;

    private BotUserAccount botUserAccount;

    private List<BotQuest> userQuests;

    private String questPublicLink;


    public BotUser(User user) {
        this.id = user.getId();
        this.telegramId = user.getTelegramId();
        this.role = user.getUserRole().getRole();
        this.userStatus = user.getUserSignInStatus().getStatus();
        this.telegramName = user.getTelegramName();
        this.avatarName = user.getAvatarName();
        this.userPoints = user.getUserPoints().getPoints();
        this.questPublicLink = user.getQuestPublicLink();
        this.botUserAccount = new BotUserAccount(user.getUserAccount().getBalance(), user.getUserAccount().isActive());


    }

    public BotQuest getQuestByName(String name){
        return userQuests.stream()
                .filter(q -> q.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public BotQuest getQuestByHashCode(long hashCode){
        return userQuests.stream()
                .filter(q -> q.hashCode() == hashCode)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotUser botUser = (BotUser) o;
        return id.equals(botUser.id) && telegramId.equals(botUser.telegramId) && Objects.equals(telegramName, botUser.telegramName) && Objects.equals(avatarName, botUser.avatarName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramId, telegramName, avatarName);
    }

    @Override
    public String toString() {
        return "BotUser{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", telegramName='" + telegramName + '\'' +
                ", avatarName='" + avatarName + '\'' +
                ", userPoints=" + userPoints;

    }
}

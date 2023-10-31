package com.zinoviev.questbot.OLD.bot.entity.models.user;
import lombok.Data;

@Data
public class BotUserAccount {

    private double balance;

    private boolean active;

    public BotUserAccount(double balance, boolean active) {
        this.balance = balance;
        this.active = active;
    }
}

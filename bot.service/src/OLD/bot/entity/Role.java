package com.zinoviev.questbot.OLD.bot.entity;

public enum Role {
    ADMIN("Администратор"),

    PLAYER("Игрок"),

    USER("Пользователь");

    String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

package com.zinoviev.entity.enums;

public enum GameRole {
    ADMIN("Администратор"),
    PLAYER("Игрок");

    String roleName;

    GameRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

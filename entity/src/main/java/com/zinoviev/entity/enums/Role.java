package com.zinoviev.entity.enums;

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

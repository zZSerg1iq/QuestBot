package com.zinoviev.orchestrator.enums;

public enum UserRoleCallback {

    CREATE_MENU("CREATE_MENU"),
    CREATE_NEW("CREATE_NEW"),
    UPLOAD_QUEST("CREATE_UPLOAD"),

    MY_QUEST_LIST("MYQ_LIST"),
    RUN_QUEST("MYQ_RUN"),
    VIEW_QUEST("MYQ_VIEW"),
    EDIT_QUEST("MYQ_EDIT"),
    REMOVE_QUEST("MYQ_REMOVE"),

    DATABASE("VIEW_DATABASE"),

    CANCEL("ACTION_CANCEL");

    private final String name;

    UserRoleCallback(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

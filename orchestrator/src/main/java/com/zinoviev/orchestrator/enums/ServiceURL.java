package com.zinoviev.orchestrator.enums;

public enum ServiceURL {

    botService("http://localhost:24001/api/bot/orch/response"),

    dataServiceUser("http://localhost:24002/api/data/userData"),
    dataServicePlayer("http://localhost:24002/api/data/playerData"),
    dataServiceActiveQuest("http://localhost:24002/api/data/activeQuestData"),
    dataServiceQuestCreation("http://localhost:24002/api/data/questCreationData"),

    questService("http://localhost:24005/api/quest/userdata");

    private final String URL;

    ServiceURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}

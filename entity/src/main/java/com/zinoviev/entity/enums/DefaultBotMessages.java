package com.zinoviev.entity.enums;


public enum DefaultBotMessages {
    /******************************************
     GENERAL
     ******************************************/

    ACTION_CANCEL("*отменено*"),

    /**
     * unexpected errors
     **/
    CALLBACK_ERROR_MESSAGE("Что-то пошло не так...\nВозможно сообщение устарело или не может быть выполнено в данное время."),
    TEXT_COMMAND_UNKNOWN(", к сожалению я не знаю такой команды. "),
    TEXT_COMMAND_SECOND_MESSAGE("Для взаимодействия используйте меню, находящееся рядом со строкой ввода. \uD83D\uDE42"),



    /******************************************
     USER
     ******************************************/

    /**
     * ---------------------
     * Sign in
     * ---------------------
     **/
    SIGN_UP_OFFER("Здравствуйте! Для того что бы пользоваться всеми функциями этого бота необходимо зарегистрироваться. \n Хотите сделать это сейчас?"),
    SIGN_UP_SELECT_NAME_TYPE("Какое имя Вы бы хотели использовать? "),
    SIGN_UP_SELECT_NAME("Прекрасно!\nПридумайте и введите имя, которое хотите использовать. \nНе волнуйтесь, его можно будет в любое время изменить в настройках аккаунта."),
    SIGN_UP_COMPLETE("Вы успешно зарегистрированы! Приятного использования!"),
    SIGN_UP_DENY("Если передумаете, возвращайтесь!"),
    CHANGE_NAME_CANCEL("Вы отменили смену имени"),


    /**
     * --------------------------
     * User
     * --------------------------
     **/
    USER_QUEST_MENU("стартовое меню квестов"),
    QUEST_CREATION_MENU("creation menu"),
    QUEST_CREATION_START("start quest creation"),
    QUEST_UPLOAD("upload quest"),
    USER_QUESTLIST_MENU("quest list menu"),
    USER_QUESTLIST_QUEST_SELECTED("quest selected"),
    USER_QUESTLIST_RUN("run quest"),
    USER_QUESTLIST_VIEW("view quest"),
    USER_QUESTLIST_UPDATE("update quest"),
    USER_QUESTLIST_REMOVE("remove quest"),
    VIEW_DATABASE_MENU("Database"),


    ACCOUNT_STATISTICS("Statistic"),






    /**
     * ---------------------
     * Help menu
     * ---------------------
     **/
    USER_HELP_MAIN_MENU_MESSAGE("Что вас интересует?"),
    USER_HELP_QUEST_CREATION_MESSAGE("Создание квеста - это обычно долгий, но очень творческий процесс! " +
            "С помощью собственного квеста Вы можете создать незабываемое приключение для своих друзей и близких!\n" +
            "и бла бла бла......."),

    USER_HELP_QUEST_UPLOADING_MESSAGE("загрузка квеста это бла бла бла бла бла бла"),

    USER_HELP_QUEST_EDITING_MESSAGE("редактирование это бла бла бла бла бла бла"),

    USER_HELP_PLAY_QUEST_MESSAGE("""
            Все очень просто: для участия в квесте отправьте в чат ссылку на запущенный квест. Она обычно выглядит примерно вот так вот:

            play:negZZYVpDF.7aXNxYqJdy.Wpm4k5ezJn.BeXoBlDvAd

            Для того что бы получить ссылку на запущенный квест можно:
            1) Запустить один из своих квестов (собственных, или тех, которые Вы приобрели или добавили в избранное)\s
            2) Запустить один из квестов, авторы которых расшарили его для всех.\s
            3) Дождаться, пока кто-нибудь запустит квест и пришлет вам ссылку.""");




    private final String message;

    DefaultBotMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

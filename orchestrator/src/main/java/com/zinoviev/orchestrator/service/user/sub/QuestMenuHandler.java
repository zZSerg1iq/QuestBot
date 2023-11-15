package com.zinoviev.orchestrator.service.user.sub;


import com.zinoviev.entity.enums.DefaultBotMessages;
import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceNames;
import com.zinoviev.orchestrator.service.MessageBuilderService;

public class QuestMenuHandler {

    /**
     * buttons callback messages
     */
    private final String QUEST_CREATION_MENU = "QUEST_CREATE_MENU";
    private final String QUEST_CREATE_NEW = "QUEST_CREATE_NEW";
    private final String UPLOAD_QUEST = "QUEST_CREATE_UPLOAD";
    private final String MY_QUEST_LIST = "QUEST_MY_LIST";
    private final String RUN_QUEST = "QUEST_MY_RUN";
    private final String VIEW_QUEST = "QUEST_MY_VIEW";
    private final String EDIT_QUEST = "QUEST_MY_EDIT";
    private final String REMOVE_QUEST = "QUEST_MY_REMOVE";
    private final String DATABASE = "QUEST_VIEW_DATABASE";
    private final String CANCEL = "ACTION_CANCEL";


    private final DataExchangeController exchangeController;
    private final UpdateData updateData;
    private final MessageBuilderService messageBuilderService;


    public QuestMenuHandler(DataExchangeController exchangeController, MessageBuilderService messageBuilderService, UpdateData updateData) {
        this.exchangeController = exchangeController;
        this.updateData = updateData;
        this.messageBuilderService = messageBuilderService;
    }

    public void showQuestMainMenu() {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.USER_QUEST_MENU.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Создание", ">", "Мои квесты", ">", "Доступные", ">", "Отмена"},
                        new String[]{QUEST_CREATION_MENU, ">", MY_QUEST_LIST, ">", DATABASE, ">", CANCEL}
                );
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    public void questMenuCallback() {
        switch (updateData.getMessage().getCallbackData()) {
            case QUEST_CREATION_MENU -> questCreationMenu();
            case MY_QUEST_LIST -> myQuests();
            case DATABASE -> viewDatabase();
        }
    }

    private void questCreationMenu() {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.QUEST_CREATION_MENU.getMessage())
                .setMessageType(updateData, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Тут пока ничего нету 11111111"},
                        new String[]{CANCEL}
                );
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void myQuests() {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.USER_QUESTLIST_MENU.getMessage())
                .setMessageType(updateData, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Тут пока ничего нету 2"},
                        new String[]{CANCEL}
                );
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void viewDatabase() {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.VIEW_DATABASE_MENU.getMessage())
                .setMessageType(updateData, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Тут пока ничего нету 3"},
                        new String[]{CANCEL}
                );
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }




    /*private void userCallBackQueryAction() {
        String query = callbackQuery.getData();
        /// QUEST MENU ///
        if (query.contains("QUEST_ACTION")) {                  //меню выбранного квеста
            selectedQuestAction(callbackQuery);
        } else if (query.contains("START_QUEST")) {           //запуск квеста
            startQuest(callbackQuery);
        } else if (query.contains("VIEW_QUEST")) {           //просмотр квеста
            viewQuest(callbackQuery);
        } else if (query.contains("EDIT_QUEST")) {           //редактирование квеста
            editQuest(callbackQuery);
        } else if (query.contains("REMOVE_QUEST")) {         //удаление квеста
            removeQuest(callbackQuery);
        } else if (query.contains("STOP_QUEST")) {         //удаление квеста
            stopQuest(callbackQuery);
        } else if (query.contains("EDIT_NODE")) {         //удаление квеста
            editNode(callbackQuery);
        }

        ///  PLAY QUEST SIGN UP ///
        else if (query.contains("PLAY_QUEST_NOW")) {       //участие в квесте после запуска
            playNow(callbackQuery);
        } else if (query.contains("SELECT_ROLE")) {                  //регистрация в квесте
            questRoleSelected(callbackQuery);
        } else {

            switch (query) {
                //основные меню
                case "QUEST_MAIN_MENU" -> showQuestMenu(callbackQuery);
                case "MY_QUEST_MENU" -> myQuestMenu(callbackQuery);
                case "STATISTIC" -> statistics(callbackQuery);

                case "ACCOUNT_MAIN_MENU" -> showAccountMenu(callbackQuery);
                case "CHANGE_AVATAR_NAME" -> changeAvatarName(callbackQuery);
                case "DONATE" -> donate(callbackQuery);

                //помощь
                case "QUEST_CREATION_HELP" -> creationQuestAbout(callbackQuery);
                case "UPLOAD_NEW_QUESTS" -> uploadQuestAbout(callbackQuery);
                case "QUEST_EDITING_HELP" -> editingQuestAbout(callbackQuery);
                case "PLAY_QUEST_ABOUT" -> playQuestAbout(callbackQuery);

                case "ACTION_CANCEL" -> cancel(callbackQuery);


                default -> botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Что-то пошло не так...\nВозможно сообщение устарело или не может быть выполнено в данное время."));
            }
        }
    }*/


    private void selectedQuestAction(String callbackData) {
        System.out.println();
       /* String[] quest = callbackData.split(":");
        long questId = Long.parseLong(quest[1]);

        boolean questIsRunning = runningQuestManager.isQuestAlreadyRunning(
                getQuestLink(
                        questService.getQuestById(questId)
                ));

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        if (!questIsRunning) {
            buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text("\uD83D\uDFE2 Запустить").callbackData("START_QUEST:" + quest[1]).build());
            buttonRows.add(buttons);
            buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text("\uD83D\uDD0E Посмотреть").callbackData("VIEW_QUEST:" + quest[1]).build());
            buttonRows.add(buttons);
            buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text("\uD83D\uDCDD Редактировать").callbackData("EDIT_QUEST:" + quest[1]).build());
            buttonRows.add(buttons);
            buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text("? Удалить").callbackData("REMOVE_QUEST:" + quest[1]).build());
            buttonRows.add(buttons);
        } else {
            buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text("\uD83D\uDD34 Остановить игру").callbackData("STOP_QUEST:" + quest[1]).build());
            buttonRows.add(buttons);
        }

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("? Отмена").callbackData("ACTION_CANCEL").build());
        buttonRows.add(buttons);
*/

/*        telegramController.sendMessage(
                MessageTemplateService.getEditedMessageTemplate(
                        callbackQuery, buttonRows, DefaultBotMessages.DISPLAY_USER_QUESTS_ACTION.getMessage()
                ));*/
    }

    private void startQuest(String callbackData) {
      /*  String[] data = callbackData.split(":");
        long questId = Long.parseLong(data[1]);

        String messageText;
        String questLink = null;
        BotQuest quest = questService.getQuestById(questId);
        BotQuestNode firstQuestNode = nodeRepositoryService.findByQuest_Id(quest.getId());

        if (firstQuestNode != null) {
            questLink = getQuestLink(quest);
            String playQuestOffer = "play:" + questLink;
            RunningBotQuest runningBotQuest = new RunningBotQuest();
            runningBotQuest.setOwnerId(botUser.getId());
            runningBotQuest.setQuestPublicLink(questLink);
            runningBotQuest.setCurrentNode(firstQuestNode);
            runningBotQuest.setQuestId(quest.getId());
            runningBotQuest.setFirstPlayerCompletesTheQuest(quest.isFirstPlayerCompletesTheQuest());
            runningQuestService.saveRunningQuest(runningBotQuest);

            RunningQuestManager.getInstance().addRunningQuest(questLink, runningBotQuest);
            messageText = DefaultBotMessages.RUNNING_QUEST_START.getMessage();
            telegramController.sendMessage(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), playQuestOffer));
        } else {
            messageText = DefaultBotMessages.RUNNING_QUEST_START_FAIL.getMessage();
        }


        telegramController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, messageText));
        if (questLink != null) {
            playQuestNowOffer(questLink, botUser, telegramController);
        }*/
    }

    private void viewQuest(String callbackData) {
        //  telegramController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Просмотр квеста пока не реализовано"));
    }

    private void editQuest(String callbackData) {
        /*BotQuestNode questNode = nodeRepositoryService.findByQuest_Id(Long.parseLong(callbackData.split(":")[1]));

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        BotQuestNode currentNode;
        while ((currentNode = questNode.getNextNode()) != null) {
            System.out.println(currentNode);
            buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text(currentNode.getStageName()).callbackData("EDIT_NODE:" + currentNode.getId()).build());
            buttonRows.add(buttons);
            questNode = currentNode;
        }

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("ACTION_CANCEL").build());
        buttonRows.add(buttons);


        telegramController.sendMessage(
                MessageTemplateService.getEditedMessageTemplate(
                        callbackQuery, buttonRows, "11111111111111111111111111111"
                ));*/

    }

    private void removeQuest(String callbackData) {
        //  telegramController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Удаление квеста пока не реализовано"));
    }

    private void stopQuest(String callbackData) {
        //  telegramController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Остановка игры пока не реализована"));
    }

    private void editNode(String callbackData) {
        //   BotQuestNode currentNode = nodeRepositoryService.findBotQuestNodeById(Long.parseLong(callbackData.split(":")[1]));
        //   telegramController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, currentNode.toString()));
    }
}

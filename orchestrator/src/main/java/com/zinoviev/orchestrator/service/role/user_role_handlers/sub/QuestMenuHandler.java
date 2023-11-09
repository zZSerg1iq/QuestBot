package com.zinoviev.orchestrator.service.role.user_role_handlers.sub;


import com.zinoviev.entity.model.UpdateData;

import java.util.ArrayList;
import java.util.List;

public class QuestMenuHandler {

    private final String CREATE = "CREATE_QUEST";
    private final String FAVOURITES = "FAVOURITES_QUEST";
    private final String DATABASE = "QUEST_DATABASE";


    private final UpdateData updateData;

    private final String username;


    public QuestMenuHandler(UpdateData updateData) {
        this.updateData = updateData;
        username = updateData.getUserData().getAvatarName() != null ? updateData.getUserData().getAvatarName() : updateData.getUserData().getFirstName();
    }


    public void showQuestMenu() {
       /* List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Создать").callbackData(CREATE).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Избранное").callbackData(FAVOURITES).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("База квестов").callbackData(DATABASE).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        telegramController.sendMessage(
                MessageTemplates.getInlineKeyboardSendMessageTemplate(updateData.getUserId(), buttonRows,
                        username + DefaultBotMessages.USER_HALLO_MESSAGE_1.getMessage()
                ));*/
    }


    public void questMenuCallback() {
        switch (updateData.getMessage().getCallbackData()) {
            case CREATE -> {
            }
            case FAVOURITES -> {
            }
            case DATABASE -> {
            }
        }
    }


    private void createNewQuest() {
        ///////////////////
    }

    private void favourites() {
        ///////////////////
        /*//List<BotQuest> quests = questService.getUserQuestList(botUser.getId()); // все квесты пользователя
        //List<BotQuest> quests = quests.addAll(questOwnersService.findAllByOwner_Id(botUser.getId())); // добавляем в список купленные квесты
        List<BotQuest> quests = getFakeQuests(3);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        String messageText = DefaultBotMessages.QUEST_LIST_NON_EMPTY.getMessage();

        if (quests.size() > 0) {
            for (BotQuest q : quests) {
                buttons = new ArrayList<>();
                buttons.add(InlineKeyboardButton.builder().text(q.getName()).callbackData("QUEST_ACTION:" + q.getId()).build());
                buttonRows.add(buttons);
            }

            buttons = new ArrayList<>();
            buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("ACTION_CANCEL").build());
            buttonRows.add(buttons);
        } else {
            messageText = DefaultBotMessages.QUEST_LIST_IS_EMPTY.getMessage();
        }


        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(
                callbackQuery, buttonRows, messageText
        ));*/
    }

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

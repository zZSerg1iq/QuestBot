package com.zinoviev.orchestrator.service.user;


import com.zinoviev.entity.enums.DefaultBotMessages;
import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceNames;
import com.zinoviev.orchestrator.service.MessageBuilderService;
import com.zinoviev.orchestrator.service.user.sub.AccountMenuHandler;
import com.zinoviev.orchestrator.service.user.sub.HelpMenuHandler;
import com.zinoviev.orchestrator.service.user.sub.QuestMenuHandler;
import lombok.Data;


@Data
public class UserRoleHandler {

    private final String CANCEL = "ACTION_CANCEL";

    private final DataExchangeController exchangeController;
    private final MessageBuilderService messageBuilderService;

    public UserRoleHandler(DataExchangeController exchangeController) {
        this.exchangeController = exchangeController;
        this.messageBuilderService = new MessageBuilderService();
    }

    public void actionHandler(UpdateData updateData) {
        if (updateData.getMessage().getCallbackData() != null) {
            userCallBackQueryAction(updateData);
        } else {
            userMessageAction(updateData);
        }
    }

    private void userMessageAction(UpdateData updateData) {
        if (updateData.getMessage().getText().equalsIgnoreCase("аккаунт")) {
            new AccountMenuHandler(exchangeController, messageBuilderService, updateData).showAccountMainMenu();

        } else if (updateData.getMessage().getText().equalsIgnoreCase("квесты")) {
            new QuestMenuHandler(exchangeController, messageBuilderService, updateData).showQuestMainMenu();

        } else if (updateData.getMessage().getText().equalsIgnoreCase("справка")) {
            new HelpMenuHandler(exchangeController, messageBuilderService, updateData).showHelpMainMenu();

        } else if (updateData.getMessage().getText().contains("play:")) {
            playQuest(updateData);

        } else {
            unknownCommandMessage(updateData);
        }
    }


    private void userCallBackQueryAction(UpdateData updateData) {
        System.out.println(">>>>>>>>>>>>>>  "+updateData.getMessage().getCallbackData());

        if (updateData.getMessage().getCallbackData().startsWith("QUEST")) {
            new QuestMenuHandler(exchangeController, messageBuilderService, updateData).questMenuCallback();

        } else if (updateData.getMessage().getCallbackData().contains("ACCOUNT")) {
            new AccountMenuHandler(exchangeController, messageBuilderService, updateData).accountMenuCallback();

        } else if (updateData.getMessage().getCallbackData().contains("HOW_TO")) {
            new HelpMenuHandler(exchangeController, messageBuilderService, updateData).helpMenuCallback();

        } else
            cancelAction(updateData);
    }


    private void playQuest(UpdateData updateData) {
        String[] tempValue = updateData.getMessage().getText().split(":");


        //if (runningQuestManager.isQuestAlreadyRunning(tempValue[1])) {
        //    questRolesOffer(tempValue[1]);
        //    return;
        //}

/*        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), DefaultBotMessages.RUNNING_QUEST_LINK_BROKEN.getMessage()
                ));*/
    }


    public void unknownCommandMessage(UpdateData updateData) {
        // переотравить заново клавиатуру и показать сообщение что команда неизвестна
        new MessageBuilderService()
                .setText( updateData,updateData.getUserData().getAvatarName() + DefaultBotMessages.TEXT_COMMAND_UNKNOWN.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.REPLY_REMOVE);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);

        new MessageBuilderService()
                .setText( updateData, DefaultBotMessages.TEXT_COMMAND_SECOND_MESSAGE.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.REPLY_ADD);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }




    private void cancelAction(UpdateData updateData) {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.ACTION_CANCEL.getMessage())
                .setKeyboardType(updateData, KeyboardType.NULL)
                .setMessageType(updateData, MessageType.DELETE);

        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }








/*
    private void playQuestNowOffer(String questLink, BotUser botUser, TelegramBotController botController) {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Да, конечно").callbackData("PLAY_QUEST_NOW:" + questLink).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Не сейчас").callbackData("PLAY_QUEST_LATER").build());
        buttonRows.add(buttons);


        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), buttonRows, DefaultBotMessages.RUNNING_QUEST_PLAY_NOW_OFFER.getMessage()
                ));
    }

    private void playNow(String callbackData) {
        String message = callbackQuery.getMessage();
        message.setText(callbackQuery.getData());

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery,
                DefaultBotMessages.RUNNING_QUEST_PLAY_NOW.getMessage()));

        playQuest(message);
    }

    private void playQuest(String message) {
        String[] tempValue = message.getText().split(":");

        if (runningQuestManager.isQuestAlreadyRunning(tempValue[1])) {
            questRolesOffer(tempValue[1]);
            return;
        }

        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), DefaultBotMessages.RUNNING_QUEST_LINK_BROKEN.getMessage()
                ));
    }

    private void questRolesOffer(String questPublicLink) {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Администратор").callbackData("SELECT_ROLE:ADMIN:" + questPublicLink).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Игрок").callbackData("SELECT_ROLE:PLAYER:" + questPublicLink).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("ACTION_CANCEL").build());
        buttonRows.add(buttons);


        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), buttonRows, DefaultBotMessages.RUNNING_QUEST_SELECT_ROLE.getMessage()
                ));
    }


    private void questRoleSelected(String callbackData) {
        String[] tempData = callbackData.split(":");

        RunningBotQuest runningBotQuest = runningQuestService.getRunningQuestByPublicLink(tempData[2]);

        if (runningBotQuest == null) {
            botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, DefaultBotMessages.RUNNING_QUEST_ADD_MEMBER_ERROR.getMessage()));
            return;
        }

        SendMessage keyboardSendMessage;
        if (tempData[1].equals("ADMIN")) {
            botUser.setRole(Role.ADMIN);
            keyboardSendMessage = RoleReplyKeyboardMarkupMenuService.getAdminReplyKeyboardMarkupMenu(callbackQuery.getMessage().getChatId(), "Добро поджаловать в админы");
        } else {
            botUser.setRole(Role.PLAYER);
            keyboardSendMessage = RoleReplyKeyboardMarkupMenuService.getPlayerReplyKeyboardMarkupMenu(callbackQuery.getMessage().getChatId(), "Добро поджаловать в игроки");
        }

        botUser.setQuestPublicLink(tempData[2]);
        userService.saveUser(botUser);

        LinkedHashMap<Object, Integer> messageMap = new LinkedHashMap<>();
        messageMap.put(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Вы успешно зарегистрированы"), 0);
        messageMap.put(keyboardSendMessage, 0);
        for (var message : runningQuestManager.addQuestMember(botUser).entrySet()) {
            messageMap.put(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), message.getKey()), message.getValue());
        }

        runningQuestService.saveQuestMembers(runningQuestManager.getQuestMembersList(botUser.getQuestPublicLink()), botUser.getQuestPublicLink());

        botController.sendMessageList(messageMap);
    }








    private void cancel(String callbackData) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    public String getQuestLink(BotQuest quest) {
        return cryptoToolService.linkOf(Math.abs(botUser.getTelegramId())) +
                "." +
                cryptoToolService.linkOf(Math.abs(botUser.getId())) +
                "." +
                cryptoToolService.linkOf(Math.abs(quest.getId())) +
                "." +
                cryptoToolService.linkOf(Math.abs(quest.getName().hashCode()));
    }

    private List<BotQuest> getFakeQuests(int questCount) {
        List<BotQuest> quests = BotQuestTemplateGenerator.getQuestList(botUser.getTelegramId(), questCount);
        botUser.setUserQuests(quests);
        questService.saveQuestList(quests);

        return questService.getUserQuestList(botUser.getId());
    }

*/


}
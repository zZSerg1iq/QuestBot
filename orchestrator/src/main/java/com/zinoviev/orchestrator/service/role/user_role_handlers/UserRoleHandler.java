package com.zinoviev.orchestrator.service.role.user_role_handlers;

import com.zinoviev.bot.controller.update.TelegramController;
import com.zinoviev.bot.entity.rest.UpdateData;
import com.zinoviev.bot.service.role.user_role_handlers.sub.AccountMenuHandler;
import com.zinoviev.bot.service.role.user_role_handlers.sub.HelpMenuHandler;
import com.zinoviev.bot.service.role.user_role_handlers.sub.QuestMenuHandler;
import com.zinoviev.bot.support.CryptoTool;
import com.zinoviev.bot.support.DefaultBotMessages;
import com.zinoviev.bot.support.MessageTemplates;
import com.zinoviev.bot.support.RoleReplyKeyboardMarkupMenuTemplates;
import lombok.Data;


@Data
public class UserRoleHandler {

    private String CANCEL = "CANCEL";

    //@Value("${invitelink.salt}");
    private String invitelink;


    private final CryptoTool cryptoTool;
    private final TelegramController telegramController;

    private String username;


    public UserRoleHandler(TelegramController telegramController) {
        cryptoTool = new CryptoTool(invitelink);
        this.telegramController = telegramController;
    }


    public void actionHandler(UpdateData updateData) {
        username = updateData.getUserData().getAvatarName() != null ? updateData.getUserData().getAvatarName() : updateData.getFirstName();

        if (updateData.getCallbackQueryData() != null) {
            userCallBackQueryAction(updateData);
        } else {
            userMessageAction(updateData);
        }
    }

    private void userMessageAction(UpdateData updateData) {
        if (updateData.getText().equalsIgnoreCase("квесты")) {
            new QuestMenuHandler(updateData, telegramController).showQuestMenu();

        } else if (updateData.getText().equalsIgnoreCase("аккаунт")) {
            new AccountMenuHandler(updateData, telegramController).showAccountMenu();

        } else if (updateData.getText().equalsIgnoreCase("гайд")) {
            new HelpMenuHandler(updateData, telegramController).showHelpMenu();

        } else if (updateData.getText().contains("play:")) {
            playQuest(updateData);

        } else {
            telegramController.sendMessage(MessageTemplates.getSendMessageTemplate(updateData.getUserId(), username + DefaultBotMessages.TEXT_COMMAND_FIRST_ERROR_MESSAGE.getMessage()));
            telegramController.sendMessage(RoleReplyKeyboardMarkupMenuTemplates.removeKeyboard(updateData.getUserId(), DefaultBotMessages.TEXT_COMMAND_SECOND_MESSAGE.getMessage()));
            telegramController.sendMessage(RoleReplyKeyboardMarkupMenuTemplates.getUserReplyKeyboardMarkupMenuMessage(updateData.getUserId(), DefaultBotMessages.TEXT_COMMAND_THIRD_MESSAGE.getMessage()));
        }
    }


    private void userCallBackQueryAction(UpdateData updateData) {
        if (updateData.getCallbackQueryData().contains("QUEST")) {
            new QuestMenuHandler(updateData, telegramController).questMenuCallback();
        } else if (updateData.getCallbackQueryData().contains("ACCOUNT")) {
            new AccountMenuHandler(updateData, telegramController).accountMenuCallback();
        } else if (updateData.getCallbackQueryData().contains("HOW_TO")) {
            new HelpMenuHandler(updateData, telegramController).helpMenuCallback();
        } else if (updateData.getCallbackQueryData().contains("CANCEL")) {
            menuCancel(updateData);
        }
    }


    private void playQuest(UpdateData updateData) {
/*        String[] tempValue = message.getText().split(":");

        if (runningQuestManager.isQuestAlreadyRunning(tempValue[1])) {
            questRolesOffer(tempValue[1]);
            return;
        }

        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), DefaultBotMessages.RUNNING_QUEST_LINK_BROKEN.getMessage()
                ));*/
    }


    private void menuCancel(UpdateData updateData) {
        telegramController.sendMessage(MessageTemplates.getMenuCancelActionTemplate(updateData));
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
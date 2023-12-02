package com.zinoviev.orchestrator.service.user;


import com.zinoviev.entity.enums.DefaultBotMessages;
import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceURL;
import com.zinoviev.orchestrator.service.MessageBuilderService;
import com.zinoviev.orchestrator.service.user.menu.service.AccountMenuHandler;
import com.zinoviev.orchestrator.service.user.menu.service.HelpMenuHandler;
import com.zinoviev.orchestrator.service.user.menu.service.QuestMenuHandler;
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

    public void actionHandler(UpdateDto updateDto) {
        if (updateDto.getMessageDto().getCallbackData() != null) {
            userCallBackQueryAction(updateDto);
        } else {
            userMessageAction(updateDto);
        }
    }

    private void userMessageAction(UpdateDto updateDto) {
        if (updateDto.getMessageDto().getText().equalsIgnoreCase("аккаунт")) {
            new AccountMenuHandler(exchangeController, messageBuilderService, updateDto).showAccountMainMenu();

        } else if (updateDto.getMessageDto().getText().equalsIgnoreCase("квесты")) {
            new QuestMenuHandler(exchangeController, messageBuilderService, updateDto).showQuestMainMenu();

        } else if (updateDto.getMessageDto().getText().equalsIgnoreCase("справка")) {
            new HelpMenuHandler(exchangeController, messageBuilderService, updateDto).showHelpMainMenu();

        } else if (updateDto.getMessageDto().getText().contains("play:")) {
            playQuest(updateDto);
        } else {
            unknownCommandMessage(updateDto);
        }
    }


    private void userCallBackQueryAction(UpdateDto updateDto) {
        if (updateDto.getMessageDto().getCallbackData().startsWith("QUEST")) {
            new QuestMenuHandler(exchangeController, messageBuilderService, updateDto).questMenuCallback();

        } else if (updateDto.getMessageDto().getCallbackData().contains("ACCOUNT")) {
            new AccountMenuHandler(exchangeController, messageBuilderService, updateDto).accountMenuCallback();

        } else if (updateDto.getMessageDto().getCallbackData().contains("HOW_TO")) {
            new HelpMenuHandler(exchangeController, messageBuilderService, updateDto).helpMenuCallback();

        } else
            cancelAction(updateDto);
    }


    private void playQuest(UpdateDto updateDto) {
        String[] tempValue = updateDto.getMessageDto().getText().split(":");

        //if (runningQuestManager.isQuestAlreadyRunning(tempValue[1])) {
        //    questRolesOffer(tempValue[1]);
        //    return;
        //}

/*        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), DefaultBotMessages.RUNNING_QUEST_LINK_BROKEN.getMessage()
                ));*/
    }


    public void unknownCommandMessage(UpdateDto updateDto) {
        // переотравить заново клавиатуру и показать сообщение что команда неизвестна
        new MessageBuilderService()
                .setText(updateDto, updateDto.getUserDTO().getGameName() + DefaultBotMessages.TEXT_COMMAND_UNKNOWN.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.REPLY_REMOVE);
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);

        new MessageBuilderService()
                .setText(updateDto, DefaultBotMessages.TEXT_COMMAND_SECOND_MESSAGE.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.REPLY_ADD);
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }




    private void cancelAction(UpdateDto updateDto) {
        messageBuilderService
                .setText(updateDto, DefaultBotMessages.ACTION_CANCEL.getMessage())
                .setKeyboardType(updateDto, KeyboardType.NULL)
                .setMessageType(updateDto, MessageType.DELETE);

        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
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



    private List<BotQuest> getFakeQuests(int questCount) {
        List<BotQuest> quests = BotQuestTemplateGenerator.getQuestList(botUser.getTelegramId(), questCount);
        botUser.setUserQuests(quests);
        questService.saveQuestList(quests);

        return questService.getUserQuestList(botUser.getId());
    }

*/


}
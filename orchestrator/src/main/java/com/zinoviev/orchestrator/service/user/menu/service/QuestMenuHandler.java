package com.zinoviev.orchestrator.service.user.menu.service;


import com.zinoviev.entity.dto.data.UserDto;
import com.zinoviev.entity.dto.data.quest.QuestDto;
import com.zinoviev.entity.enums.DefaultBotMessages;
import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.enums.RequestType;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceURL;
import com.zinoviev.orchestrator.service.MessageBuilderService;
import com.zinoviev.orchestrator.support.CryptoTool;
import org.springframework.beans.factory.annotation.Value;

public class QuestMenuHandler {

    /**
     * buttons callback messages
     */
    private final String QUEST_CREATION_MENU = "QUEST_CREATION_MENU";
    private final String QUEST_CREATE_NEW = "QUEST_CREATE_NEW";
    private final String UPLOAD_QUEST = "QUEST_UPLOAD_FILE";
    private final String MY_QUEST_LIST = "QUEST_MY_QUESTS";
    private final String RUN_QUEST = "QUEST_RUN";
    private final String VIEW_QUEST = "QUEST_VIEW";
    private final String EDIT_QUEST = "QUEST_EDIT";
    private final String REMOVE_QUEST = "QUEST_REMOVE";

    private final String DATABASE = "QUEST_VIEW_DATABASE";
    private final String CANCEL = "ACTION_CANCEL";



    @Value("${inviteLink.salt}")
    private String inviteLinkSalt;

    private final CryptoTool cryptoTool;

    private final DataExchangeController exchangeController;
    private final UpdateDto updateDto;
    private final MessageBuilderService messageBuilderService;


    public QuestMenuHandler(DataExchangeController exchangeController, MessageBuilderService messageBuilderService, UpdateDto updateDto) {
        this.exchangeController = exchangeController;
        this.updateDto = updateDto;
        this.messageBuilderService = messageBuilderService;
        cryptoTool = new CryptoTool(inviteLinkSalt);
    }

    public void showQuestMainMenu() {
        messageBuilderService
                .setText(updateDto, DefaultBotMessages.USER_QUEST_MENU.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Создание", null, "Мои квесты", null, "Доступные", null, "Отмена"},
                        new String[]{QUEST_CREATION_MENU, null, MY_QUEST_LIST, null, DATABASE, null, CANCEL}
                );
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    public void questMenuCallback() {
        switch (updateDto.getMessageDto().getCallbackData()) {
            case QUEST_CREATION_MENU -> questCreationMenu();
            case MY_QUEST_LIST -> myQuests();
            case DATABASE -> viewDatabase();
            case RUN_QUEST -> runQuest();
        }
    }


    private void questCreationMenu() {
        messageBuilderService
                .setText(updateDto, DefaultBotMessages.QUEST_CREATION_MENU.getMessage())
                .setMessageType(updateDto, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Тут пока ничего нету 11111111"},
                        new String[]{CANCEL}
                );
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }


    //TODO Здесь должен выводиться список квестов, которые уже есть
    // а для этого надо создать отдельный класс для генерации мок-квестов
    // и располагаться он должен скорее всего в БД сервисе или квест сервисе
    private void myQuests() {


        /*messageBuilderService
                .setText(updateDto, DefaultBotMessages.USER_QUESTLIST_MENU.getMessage())
                .setMessageType(updateDto, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Тут пока ничего нету 2", "Запуск теста"},
                        new String[]{CANCEL, RUN_QUEST}
                );

        exchangeController.exchangeWith(ServiceURL.botService, updateDto);*/
    }

    private void viewDatabase() {
        messageBuilderService
                .setText(updateDto, DefaultBotMessages.VIEW_DATABASE_MENU.getMessage())
                .setMessageType(updateDto, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Тут пока ничего нету 3"},
                        new String[]{CANCEL}
                );
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void runQuest() {
        QuestDto questDto = new QuestDto();
        questDto.setQuestName("TEST QUEST");
        questDto.setAuthor(updateDto.getUserDTO().getId());
        questDto.setDescription("QUEST DESCRIPTION");
        updateDto.setRequestType(RequestType.SAVE_ONLY);
        exchangeController.exchangeWith(ServiceURL.dataServiceActiveQuest, updateDto);

        //TODO остановился на том, что бы

      /*  QuestNodeDto questNodeDto = new QuestNodeDto();
        questNodeDto.setStageName("TEST NODE");







        PlayerDto playerDto = new PlayerDto();
        playerDto.setActiveQuest(123);
        playerDto.setUserId(updateDto.getUserDTO().getId());
        playerDto.setScore(1000000);

        updateDto.getUserDTO().setPlayer(playerDto);
        System.out.println("--------------------------SAVE----------------------------");
        System.out.println(updateDto);
*/
       // exchangeController.sendSaveEntityRequest(updateDto);



        /*messageBuilderService
                .setText(updateDto, DefaultBotMessages.QUEST_CREATION_MENU.getMessage())
                .setMessageType(updateDto, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Тут пока ничего нету 11111111"},
                        new String[]{CANCEL}
                );
        exchangeController.exchangeWith(ServiceNames.BOT_SERVICE, updateDto);*/
    }

    public String getInviteLink(QuestDto quest) {
        UserDto user = updateDto.getUserDTO();

        return cryptoTool.linkOf(Math.abs(user.getTelegramId())) +
                "." +
                cryptoTool.linkOf(Math.abs(user.getId())) +
                "." +
                cryptoTool.linkOf(Math.abs(quest.getId())) +
                "." +
                cryptoTool.linkOf(Math.abs(quest.getQuestName().hashCode()));
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

package com.zinoviev.orchestrator.service.role.player_role_handlers;

import com.zinoviev.entity.model.UpdateData;
import lombok.Data;


@Data
public class PlayerRoleHandler{


    public void actionHandler(UpdateData updateData) {

    }

    /*
    private final UserRepositoryService userService;
    private final QuestRepositoryService questService;
    private final RunningQuestRepositoryService runningQuestService;



    private final RunningQuestManager runningQuestManager;



    private final TelegramBotController botController;

    private final BotUser botUser;

    long id = 0;

    public PlayerRoleHandler(UserRepositoryService userService, QuestRepositoryService questService, RunningQuestRepositoryService runningQuestService, TelegramBotController botController, BotUser botUser) {
        this.runningQuestService = runningQuestService;
        this.userService = userService;
        this.questService = questService;
        this.botController = botController;
        this.botUser = botUser;
        runningQuestManager = RunningQuestManager.getInstance();
    }

    @Override
    public void action(Update update) {
        RunningBotQuestMember questMember = runningQuestManager.getQuestMember(botUser.getQuestPublicLink(), botUser.getId());

        if (update.hasMessage() && update.getMessage().hasText()){
            if (update.getMessage().getText().equals("Справка")) {
                helpMenu();
                return;
            }
        }


        if (questMember.getMuteUserState() != null) {
            long timeNow;
            if (update.hasMessage()) {
                timeNow = update.getMessage().getDate();
            } else {
                timeNow = update.getCallbackQuery().getMessage().getDate();
            }
            long bannedTime = questMember.getMuteUserState().getMuteEndMessageTime() - timeNow;

            if (bannedTime <= 0) {
                questMember.setMuteUserState(null);
                runningQuestService.saveQuestMembers(runningQuestManager.getQuestMembersList(botUser.getQuestPublicLink()), botUser.getQuestPublicLink());
            } else {
                if (bannedTime > 60) {
                    bannedTime /= 60;
                }
                botController.sendMessage(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), "Ваш чат заблокирован\n" +
                        "Блокировка спадет через " + bannedTime + " минут"));
                return;
            }
        }

        // если есть какое-то сообщение в чате - обрабатываем его
        if (update.hasMessage()) {
            playerMessageAction(update);
        } else {
            // если все предыдущие условия не выполнены, значит апдэйт от пользователя содержит только колбэк
            playerCallBackQueryAction(update.getCallbackQuery());
        }
    }

    private void playerMessageAction(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equalsIgnoreCase("справка")) {
            helpMenu();
        } else {
            QuestResponseModel responseModel =
            runningQuestManager.playerUpdateHandler(botUser.getQuestPublicLink(), botUser.getId(), update);

            if (responseModel.getTextMessageResponse() != null){
                responseModel.getTextMessageResponse().forEach((s, integer) ->
                        botController.sendMessage(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), s)));

            }

            if (responseModel.getGeoPositionResponse() != null){
                responseModel.getGeoPositionResponse().forEach((s, integer) ->
                        botController.sendMessage(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), s)));

            }
            if (responseModel.getGeoPositionDistanceMessage() != null) {
                botController.sendMessage(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), responseModel.getGeoPositionDistanceMessage()));
            }

            if (responseModel.getTextMessageResponse() != null || responseModel.getGeoPositionResponse() != null){
                responseModel.getTextMessageResponse().forEach((s, integer) ->
                    botController.sendMessage(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), s)));
            }
            if (responseModel.isTheLastNode()){
                botController.sendMessage(MessageTemplateService.getSendMessageTemplate(botUser.getTelegramId(), "Квест завершен"));

                if (runningQuestManager.removeQuestByPublicLink(botUser.getQuestPublicLink(), botUser.getId())){
                    runningQuestService.deleteByQuestPublicLink(botUser.getQuestPublicLink());
                }
            }
        }

    }

    private void playerCallBackQueryAction(CallbackQuery callbackQuery) {
        String query = callbackQuery.getData();

        if (query.contains("BANISH_CONFIRM")){
            LeaveAction(callbackQuery);
        } else {
            switch (query) {
                //Меню действий с квестом
                case "PLAY_QUEST_ABOUT" -> playQuestAbout(callbackQuery);
                case "LEAVE_QUEST" -> leaveQuest(callbackQuery);

                //Отмена
                case "CANCEL" -> cancel(callbackQuery);

                default -> botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Что-то пошло не так...\nВозможно сообщение устарело или не может быть выполнено в данное время."));
            }
        }
    }

    private void playQuestAbout(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Что-то пошло не так...\nВозможно сообщение устарело или не может быть выполнено в данное время."));
    }



    private void helpMenu() {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Как играть и что делать").callbackData("PLAY_QUEST_ABOUT").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Покинуть квест").callbackData("LEAVE_QUEST").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);


        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), buttonRows, DefaultBotMessages.USER_HELP_MESSAGE.getMessage()
                ));
    }


    private void leaveQuest(CallbackQuery callbackQuery) {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Вы уверены?").callbackData("BANISH_CONFIRM="+botUser.getId()).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(
                callbackQuery, buttonRows, "Вы уверены, что хотите покинуть квест?"));
    }

    private void LeaveAction(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("=");

        BotUser selectedUser = userService.getBotUserById(Long.parseLong(data[1]));
        runningQuestManager.banishMember(selectedUser);

        RunningBotQuest runningBotQuest = runningQuestService.getRunningQuestByPublicLink(selectedUser.getQuestPublicLink());
        runningBotQuest.getMembers().stream()
                .filter(member -> member.getUser().getId().equals(selectedUser.getId()))
                .findFirst().ifPresent(user -> user.setActive(false));

        runningQuestService.saveQuestMembers(runningQuestManager.getQuestMembersList(selectedUser.getQuestPublicLink()), selectedUser.getQuestPublicLink());

        selectedUser.setQuestPublicLink(null);
        selectedUser.setRole(Role.USER);

        userService.saveUser(selectedUser);

        if (selectedUser.equals(botUser)){
            botController.sendMessage(RoleReplyKeyboardMarkupMenuService.getUserReplyKeyboardMarkupMenu(selectedUser.getTelegramId(), "При желании, Вы всегда можете вернуться, используя старую ссылку на квест."));
            botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Вы покинули квест"));
        } else {
            botController.sendMessage(RoleReplyKeyboardMarkupMenuService.getUserReplyKeyboardMarkupMenu(selectedUser.getTelegramId(), "Вы были изгнаны из квеста"));
            botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Пользователь изгнан из квеста"));
        }
    }


    private void cancel(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }*/

}

package com.zinoviev.bot.service.role.admin_role_handlers;

import com.zinoviev.bot.entity.rest.UpdateData;
import lombok.Data;


@Data
public class AdminRoleHandler{


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

    public AdminRoleHandler(UserRepositoryService userService, QuestRepositoryService questService, RunningQuestRepositoryService runningQuestService, TelegramBotController botController, BotUser botUser) {
        this.runningQuestService = runningQuestService;
        this.userService = userService;
        this.questService = questService;
        this.botController = botController;
        this.botUser = botUser;
        runningQuestManager = RunningQuestManager.getInstance();
    }

    @Override
    public void action(Update update) {
        id = update.hasMessage()? update.getMessage().getChatId(): update.getCallbackQuery().getFrom().getId();
        RunningBotQuestMember questMember = runningQuestManager.getQuestMember(botUser.getQuestPublicLink(), botUser.getId());

        if (questMember.getMuteUserState() != null){
            long timeNow = 0;
            if (update.hasMessage()){
                timeNow = update.getMessage().getDate();
            } else {
                timeNow = update.getCallbackQuery().getMessage().getDate();
            }
            long bannedTime = questMember.getMuteUserState().getMuteEndMessageTime() - timeNow;

            if (bannedTime <= 0){
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
        if (update.hasMessage()){
            adminMessageAction(update.getMessage());
        } else {
            // если все предыдущие условия не выполнены, значит апдэйт от пользователя содержит только колбэк
            adminCallBackQueryAction(update.getCallbackQuery());
        }
    }


    private void adminMessageAction(Message message) {
        if (message.getText().equalsIgnoreCase("квест")){
            questOptions();
        } else if (message.getText().equalsIgnoreCase("игроки")){
            playersOptions();
        } else if (message.getText().equalsIgnoreCase("помощь")) {
            helpMenu();
        }
    }

    private void adminCallBackQueryAction(CallbackQuery callbackQuery) {
        String query = callbackQuery.getData();

        if (query.contains("USER_ACTION_MENU")){
             userActionMenu(callbackQuery);
        } else if (query.contains("SET_PENALTY")){
             penaltyAction(callbackQuery);
         } else if (query.contains("SET_ROLE")){
             changeRoleAction(callbackQuery);
        } else if (query.contains("BANISH_USER")){          //Изгнать из квеста
            banishUserConfirm(callbackQuery);
        } else if (query.contains("BANISH_CONFIRM")){
            banishUserAction(callbackQuery);
        } else if (query.contains("MUTE_SELECT_TIME")){     //Дать банчат
            muteUserActionSelectTime(callbackQuery);
        } else if (query.contains("MUTE_EXECUTE_ACTION")){  // подтвердить банчат
            muteUserAction(callbackQuery);
        } else  if (query.contains("CANCEL_MUTE")){         //Снять банчат
            muteCancelConfirm(callbackQuery);
        } else if (query.contains("MUTE_CANCEL_CONFIRM")){   // подтвердить снятие банчата
            muteCancelAction(callbackQuery);
        } else if (query.contains("SEND_PRIVATE_MESSAGE")){   // подтвердить снятие банчата
            sendPrivateMessage(callbackQuery);
        } else {
            switch (query) {
                //Меню действий с квестом
                case "RESET_QUEST" -> resetQuest(callbackQuery);
                case "SKIP_QUEST_PARTS" -> skipQuestParts(callbackQuery);
                case "NEXT_QUEST_PART" -> questNextPart(callbackQuery);
                case "QUEST_PAUSE" -> pauseQuest(callbackQuery);
                case "LEAVE_QUEST" -> leaveQuest(callbackQuery);



                //Меню действий с игроками
                case "PLAYERS_ACTIONS" -> showPlayersListAndActions(callbackQuery);
                case "PLAYERS_STATISTICS" -> showPlayersStatistics(callbackQuery);
                case "MESSAGE_TO_ALL" -> messageToAll(callbackQuery);
                

                //Меню помощи
                case "HELP_ABOUT" -> showAdminRoleHelp(callbackQuery);
                case "HELP_ACTIONS" -> showAdminActionsHelp(callbackQuery);


                //Отмена
                case "CANCEL" -> cancel(callbackQuery);

                default -> botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Что-то пошло не так...\nВозможно сообщение устарело или не может быть выполнено в данное время."));
            }
        }
    }




    private void questOptions() {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Перезапуск").callbackData("RESET_QUEST").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Пропустить n этапов").callbackData("SKIP_QUEST_PARTS").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Следующий этап").callbackData("NEXT_QUEST_PART").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Пауза").callbackData("QUEST_PAUSE").build());
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

    private void playersOptions() {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Действия с игроками").callbackData("PLAYERS_ACTIONS").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Сообщение всем").callbackData("MESSAGE_TO_ALL").build());
        buttonRows.add(buttons);
        
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Статистика").callbackData("PLAYERS_STATISTICS").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), buttonRows, DefaultBotMessages.USER_HELP_MESSAGE.getMessage()
                ));
    }

    private void helpMenu() {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Роль администратора").callbackData("HELP_ABOUT").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Команды и действия").callbackData("HELP_ACTIONS").build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(
                MessageTemplateService.getSendMessageTemplate(
                        botUser.getTelegramId(), buttonRows, DefaultBotMessages.USER_HELP_MESSAGE.getMessage()
                ));
    }





    private void resetQuest(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void skipQuestParts(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void questNextPart(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void pauseQuest(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void showPlayersListAndActions(CallbackQuery callbackQuery) {
        List<RunningBotQuestMember> questMembers = runningQuestManager.getQuestMembersList(botUser.getQuestPublicLink());

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        for (var user: questMembers) {
            buttons = new ArrayList<>();
            String role = "";

            if (!user.isActive()){
                continue;
            }

            if (user.getUser().getRole() == Role.ADMIN){
                role = "  ("+user.getUser().getRole().getRoleName()+")";
            }

            buttons.add(InlineKeyboardButton
                    .builder()
                    .text(
                            user.getUser().getAvatarName()
                            +role
                    ).callbackData("USER_ACTION_MENU="+user.getUser().getId()).build());
            buttonRows.add(buttons);
        }

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(
                MessageTemplateService.getEditedMessageTemplate(
                        callbackQuery, buttonRows, "Выберите участника"
                ));
    }

    private void userActionMenu(CallbackQuery callbackQuery) {
        System.out.println(callbackQuery.getData());

        long userId = Long.parseLong(callbackQuery.getData().split("=")[1]);
        BotUser selectedUser = userService.getBotUserById(userId);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отравить сообщение").callbackData("SEND_PRIVATE_MESSAGE="+userId).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Дать штраф с пояснением").callbackData("SET_PENALTY="+userId).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();

        RunningBotQuestMember questMember = runningQuestManager.getQuestMember(botUser.getQuestPublicLink(), userId);
        System.out.println(questMember);
        if (questMember.getMuteUserState() == null){
            buttons.add(InlineKeyboardButton.builder().text("Заблокировать чат").callbackData("MUTE_SELECT_TIME=" + userId).build());
        } else {
            buttons.add(InlineKeyboardButton.builder().text("Разблокировать чат").callbackData("CANCEL_MUTE=" + userId).build());
        }
        buttonRows.add(buttons);

        buttons = new ArrayList<>();

       if (selectedUser.getRole() == Role.PLAYER) {
            buttons.add(InlineKeyboardButton.builder().text("Сделать администратором").callbackData("SET_ROLE=ADMIN="+userId).build());
        } else {
            buttons.add(InlineKeyboardButton.builder().text("Сделать игроком").callbackData("SET_ROLE=PLAYER="+userId).build());
        }
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Изгнать с квеста").callbackData("BANISH_USER="+userId).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(
                MessageTemplateService.getEditedMessageTemplate(
                        callbackQuery, buttonRows, "Выберите действие, которое будет применено к пользователю "+selectedUser.getAvatarName()
                ));
    }

    private void showPlayersStatistics(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void messageToAll(CallbackQuery callbackQuery) {
        //сначала надо ввести сообщение
        List<BotUser> questUserList = runningQuestManager.getQuestUserList(botUser.getQuestPublicLink());
        //questMembers.forEach(botUser1 -> botController.sendMessage());

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void showAdminRoleHelp(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void showAdminActionsHelp(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void cancel(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
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

    private void banishUserConfirm(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("=");
        long userId = Long.parseLong(data[1]);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Изгнать").callbackData("BANISH_CONFIRM="+userId).build());
        buttonRows.add(buttons);
       
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(
                        callbackQuery, buttonRows, "Изгнать пользователя "+botUser.getAvatarName()+ " из вашего квеста?"
                ));
    }


    private void banishUserAction(CallbackQuery callbackQuery) {
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







    private void muteUserActionSelectTime(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("=");
        long userId = Long.parseLong(data[1]);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("1 минута").callbackData("MUTE_EXECUTE_ACTION="+userId+"="+60).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("5 минут").callbackData("MUTE_EXECUTE_ACTION="+userId+"="+300).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("10 минут").callbackData("MUTE_EXECUTE_ACTION="+userId+"="+600).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("30 минуты").callbackData("MUTE_EXECUTE_ACTION="+userId+"="+1800).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("1 час").callbackData("MUTE_EXECUTE_ACTION="+userId+"="+3600).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Навсегда").callbackData("MUTE_EXECUTE_ACTION="+userId+"="+999999).build());
        buttonRows.add(buttons);

        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, buttonRows, "Выберите время блокировки чата игрока"));
    }

    private void muteUserAction(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("=");
        long userId = Long.parseLong(data[1]);
        int time = Integer.parseInt(data[2]);

        BotUser selectedUser = userService.getBotUserById(userId);

        String timeString;

        if (time / 60 > 9999){
            timeString = "всегда.";
        } else if (time / 60 > 59 && time / 60 < 9999){
            timeString = " один час.";
        } else {
            timeString = " "+(time / 60) + " минут";
        }

        RunningBotQuestMuteUserState mute = new RunningBotQuestMuteUserState();
        mute.setMuteStartMessageTime(callbackQuery.getMessage().getDate());
        mute.setMuteEndMessageTime(callbackQuery.getMessage().getDate() + time);
        runningQuestManager.getQuestMember(botUser.getQuestPublicLink(), selectedUser.getId())
                .setMuteUserState(mute);
        runningQuestService.saveQuestMembers(runningQuestManager.getQuestMembersList(botUser.getQuestPublicLink()), botUser.getQuestPublicLink());

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Чат пользователя "+ selectedUser.getAvatarName()+ " заблокирован на"+timeString));
    }


    private void muteCancelConfirm(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("=");
        long userId = Long.parseLong(data[1]);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Разблокировать чат").callbackData("MUTE_CANCEL_CONFIRM="+userId).build());
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, buttonRows,
                "Разблокровать чат пользователя "+botUser.getAvatarName()+" ?"));
    }

    private void muteCancelAction(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("=");
        long userId = Long.parseLong(data[1]);

        runningQuestManager.getQuestMember(botUser.getQuestPublicLink(), userId)
                .setMuteUserState(null);
        runningQuestService.saveQuestMembers(
                runningQuestManager.getQuestMembersList(botUser.getQuestPublicLink()), botUser.getQuestPublicLink()
        );
        System.out.println(runningQuestManager.getQuestMembersList(botUser.getQuestPublicLink()));

        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Чат пользователя разблокирован!"));
    }

    private void sendPrivateMessage(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }
    
    private void penaltyAction(CallbackQuery callbackQuery) {
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "- Отменено -"));
    }

    private void changeRoleAction(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split("=");
        String chosenRole = data[1];
        long userId = Long.parseLong(data[2]);
        BotUser selectedUser = userService.getBotUserById(userId);

        if (chosenRole.equals("PLAYER")){
            selectedUser.setRole(Role.PLAYER);
            botController.sendMessage(RoleReplyKeyboardMarkupMenuService.getPlayerReplyKeyboardMarkupMenu(selectedUser.getTelegramId(), "Вы стали игроком"));
            runningQuestManager.addQuestMember(selectedUser);
        } else {
            selectedUser.setRole(Role.ADMIN);
            botController.sendMessage(RoleReplyKeyboardMarkupMenuService.getAdminReplyKeyboardMarkupMenu(selectedUser.getTelegramId(), "Вы стали администратором"));
            runningQuestManager.addQuestMember(selectedUser);
        }
        userService.saveUser(selectedUser);
        botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(callbackQuery, "Роль успешно изменена"));
    }
*/


}

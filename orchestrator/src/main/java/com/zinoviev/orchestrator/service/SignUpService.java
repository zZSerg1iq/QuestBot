package com.zinoviev.orchestrator.service;

import com.zinoviev.entity.enums.*;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceNames;


public class SignUpService {

    private final String ACCEPTED = "REG_ACCEPTED";
    private final String DENY = "REG_DENY";
    private final String NEW_NAME = "REG_NEW_NAME";
    private final String USER_NAME = "REG_USER_NAME";


    private final DataExchangeController exchangeController;

    private final MessageBuilderService messageBuilderService;

    public SignUpService(DataExchangeController exchangeController) {
        this.exchangeController = exchangeController;
        this.messageBuilderService = new MessageBuilderService();
    }

    public void proceedSignUp(UpdateData updateData) {
        selectSignUpStage(updateData);
    }


    private void selectSignUpStage(UpdateData updateData) {
        if (updateData.getMessage().getCallbackData() != null && updateData.getMessage().getCallbackData().equals(DENY)) {
            regCancel(updateData);
            return;
        }

        switch (updateData.getUserData().getSignInStatus()) {
            case SIGN_UP_NONE -> userSignUpShowOffer(updateData);
            case SIGN_UP_OFFER -> userSignUpSelectNameType(updateData);
            case SIGN_UP_SELECT_NAME_TYPE -> userSignUpNameTypeCallbackHandler(updateData);
            case SIGN_UP_SELECT_NAME -> userSignUpAcceptNewName(updateData);
        }
    }


    private void userSignUpShowOffer(UpdateData updateData) {
        System.out.println(">>> SIGN UP OFFER <<<");
        dbSaveRequest(updateData, SignInStatus.SIGN_UP_OFFER);

        messageBuilderService
                .setText(updateData, DefaultBotMessages.SIGN_UP_OFFER.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Да, конечно", "Нет, спасибо"},
                        new String[]{ACCEPTED, DENY});

        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void userSignUpSelectNameType(UpdateData updateData) {
        System.out.println(">>> SELECT NAME TYPE <<<" + updateData.getMessage().getCallbackData());
        MessageType messageType = MessageType.EDIT_MESSAGE;

        if (updateData.getMessage().getCallbackData() == null) {
            messageType = MessageType.MESSAGE;
        }

        if (updateData.getMessage().getCallbackData() != null) {
            if (updateData.getMessage().getCallbackData().equals(ACCEPTED)) {
                dbSaveRequest(updateData, SignInStatus.SIGN_UP_SELECT_NAME_TYPE);
                messageType = MessageType.EDIT_MESSAGE;
            }
        }

        messageBuilderService
                .setText(updateData, DefaultBotMessages.SIGN_UP_SELECT_NAME_TYPE.getMessage())
                .setMessageType(updateData, messageType)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Я его придумаю", updateData.getMessage().getFirstName(), "Возможно позже"},
                        new String[]{NEW_NAME, USER_NAME, DENY}
                );

        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void userSignUpNameTypeCallbackHandler(UpdateData updateData) {
        System.out.println(">>> SELECT NAME TYPE CALLBACK<<<" + updateData.getMessage().getCallbackData());

        if (updateData.getMessage().getCallbackData() != null) {
            updateData.setRequestStatus(RequestStatus.SAVE_ONLY);

            if (updateData.getMessage().getCallbackData().equals(NEW_NAME)) {
                userSignUpNewNameSelected(updateData);
            } else if (updateData.getMessage().getCallbackData().equals(USER_NAME)) {
                userSignUpUserNameSelected(updateData);
            } else {
                regCancel(updateData);
            }
        } else {
            userSignUpSelectNameType(updateData);
        }
    }

    private void userSignUpNewNameSelected(UpdateData updateData) {
        System.out.println(">>> NEW NAME <<<");
        dbSaveRequest(updateData, SignInStatus.SIGN_UP_SELECT_NAME);

        messageBuilderService
                .setText(updateData, DefaultBotMessages.SIGN_UP_SELECT_NAME.getMessage())
                .setMessageType(updateData, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateData, KeyboardType.NULL);

        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void userSignUpUserNameSelected(UpdateData updateData) {
        System.out.println(">>> USER NAME <<<");
        updateData.getUserData().setAvatarName(updateData.getMessage().getFirstName());
        dbSaveRequest(updateData, SignInStatus.SIGN_UP_COMPLETE);

        messageBuilderService
                .setText(updateData, DefaultBotMessages.SIGN_UP_COMPLETE.getMessage())
                .setMessageType(updateData, MessageType.DELETE);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);

        messageBuilderService
                .setText(updateData, DefaultBotMessages.SIGN_UP_COMPLETE.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.REPLY_ADD);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void userSignUpAcceptNewName(UpdateData updateData) {
        System.out.println(">>> ACCEPT NEW NAME <<<");
        updateData.getUserData().setAvatarName(updateData.getMessage().getText());
        dbSaveRequest(updateData, SignInStatus.SIGN_UP_COMPLETE);

        messageBuilderService
                .setText(updateData, DefaultBotMessages.SIGN_UP_COMPLETE.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.REPLY_ADD);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void regCancel(UpdateData updateData) {
        System.out.println(">>> REG CANCEL <<<");
        SignInStatus signInStatus;
        String message;
        MessageType type;

        if (updateData.getUserData().getAvatarName() == null) {
            signInStatus = SignInStatus.SIGN_UP_NONE;
            message = DefaultBotMessages.SIGN_UP_DENY.getMessage();
        } else {
            signInStatus = SignInStatus.SIGN_UP_COMPLETE;
            message = DefaultBotMessages.CHANGE_NAME_CANCEL.getMessage();
        }

        if (updateData.getMessage().getCallbackData() == null) {
            type = MessageType.MESSAGE;
        } else {
            type = MessageType.EDIT_MESSAGE;
        }

        messageBuilderService
                .setText(updateData, message)
                .setMessageType(updateData, type);

        dbSaveRequest(updateData, signInStatus);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void dbSaveRequest(UpdateData updateData, SignInStatus signInStatus) {
        updateData.getUserData().setSignInStatus(signInStatus);
        updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
        exchangeController.sendDataTo(ServiceNames.DATA_SERVICE, updateData);
    }


}

package com.zinoviev.orchestrator.service;

import com.zinoviev.entity.enums.DefaultBotMessages;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.enums.RequestStatus;
import com.zinoviev.entity.enums.SignInStatus;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.updatedata.entity.InlineButton;
import com.zinoviev.entity.model.updatedata.entity.Message;
import com.zinoviev.entity.model.updatedata.entity.ReplyKeyboardType;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceNames;

import java.util.ArrayList;
import java.util.List;


public class SignUpService {

    private final DataExchangeController exchangeController;

    public SignUpService(DataExchangeController exchangeController) {
        this.exchangeController = exchangeController;
    }

    public void proceedSignUp(UpdateData updateData) {
        System.out.println("user reg service: ");
        selectSignUpStage(updateData);
    }

    private void selectSignUpStage(UpdateData updateData) {
        System.out.println(".... select stage");
        switch (updateData.getUserData().getSignInStatus()) {
            case SIGN_IN_NONE -> userSignUpShowOffer(updateData);
            case SIGN_IN_OFFER -> userSignUpSelectNameType(updateData);
            case SIGN_IN_SELECT_NAME_TYPE -> userSignNameTypeCallbackHandler(updateData);
            case SIGN_IN_SELECT_NAME -> userSignUpAcceptNewName(updateData);
        }
    }


    private void userSignUpShowOffer(UpdateData updateData) {
        System.out.println(".... sign in offer");
        dbSaveRequest(updateData, SignInStatus.SIGN_IN_OFFER);

        Message message = updateData.getMessage();
        message.setMessageType(MessageType.MESSAGE);
        message.setKeyboardType(ReplyKeyboardType.INLINE);
        message.setText(DefaultBotMessages.SIGN_UP_OFFER.getMessage());

        List<List<InlineButton>> buttonRows = new ArrayList<>();
        List<InlineButton> buttons = new ArrayList<>();
        buttons.add(new InlineButton.Builder().setText("Да, конечно").setCallbackData("REG_ACCEPTED").build());
        buttons.add(new InlineButton.Builder().setText("Нет, спасибо").setCallbackData("REG_DENY").build());
        buttonRows.add(buttons);

        message.setButtons(buttonRows);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void userSignUpSelectNameType(UpdateData updateData) {
        System.out.println(".... select name type");

        if (updateData.getMessage().getCallbackData() != null) {
            if (updateData.getMessage().getCallbackData().equals("REG_ACCEPTED")) {
                dbSaveRequest(updateData, SignInStatus.SIGN_IN_SELECT_NAME_TYPE);

                Message message = updateData.getMessage();
                message.setMessageType(MessageType.EDIT_MESSAGE);
                message.setKeyboardType(ReplyKeyboardType.INLINE);
                message.setText(DefaultBotMessages.SIGN_UP_SELECT_NAME_TYPE.getMessage());

                List<List<InlineButton>> buttonRows = new ArrayList<>();
                List<InlineButton> buttons = new ArrayList<>();
                buttons.add(new InlineButton.Builder().setText("Я его придумаю").setCallbackData("REG_NEW_NAME").build());
                buttons.add(new InlineButton.Builder().setText(updateData.getMessage().getFirstName()).setCallbackData("REG_USER_NAME").build());
                buttons.add(new InlineButton.Builder().setText("Нет, спасибо").setCallbackData("REG_DENY").build());
                buttonRows.add(buttons);

                message.setButtons(buttonRows);
                exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
            } else {
                regCancel(updateData);
            }
        }
    }


    private void userSignNameTypeCallbackHandler(UpdateData updateData) {
        System.out.println(".... select name callback: ");
        if (updateData.getMessage().getCallbackData() != null) {
            updateData.setRequestStatus(RequestStatus.SAVE_ONLY);

            System.out.println(".... new name");

            if (updateData.getMessage().getCallbackData().equals("REG_NEW_NAME")) {
                dbSaveRequest(updateData, SignInStatus.SIGN_IN_SELECT_NAME);

                Message message = updateData.getMessage();
                message.setMessageType(MessageType.EDIT_MESSAGE);
                message.setKeyboardType(ReplyKeyboardType.INLINE);
                message.setText(DefaultBotMessages.SIGN_UP_SELECT_NAME.getMessage());

                exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);

            } else if (updateData.getMessage().getCallbackData().equals("REG_USER_NAME")) {
                System.out.println(".... use user name");
                //save data
                updateData.getUserData().setAvatarName(null);
                dbSaveRequest(updateData, SignInStatus.SIGN_IN_COMPLETE);

                 //send message about reg complete
                Message message = updateData.getMessage();
                message.setMessageType(MessageType.EDIT_MESSAGE);
                message.setKeyboardType(ReplyKeyboardType.INLINE);
                message.setText(DefaultBotMessages.SIGN_UP_COMPLETE.getMessage());
                exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);

                //send reply keyboard
                message.setMessageType(MessageType.MESSAGE);
                message.setKeyboardType(ReplyKeyboardType.REPLY_ADD);
                message.setText("*Вам добавлена пользовательская панель управления*");
                exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
            } else {
                regCancel(updateData);
            }
        } else {
            userSignUpAcceptNewName(updateData);
        }
    }


    private void userSignUpAcceptNewName(UpdateData updateData) {
        updateData.getUserData().setAvatarName(updateData.getMessage().getText());
        dbSaveRequest(updateData, SignInStatus.SIGN_IN_COMPLETE);

        Message message = updateData.getMessage();
        message.setMessageType(MessageType.MESSAGE);
        message.setKeyboardType(ReplyKeyboardType.REPLY_ADD);
        message.setText(DefaultBotMessages.SIGN_UP_COMPLETE.getMessage());

        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }


    private void regCancel(UpdateData updateData) {
        System.out.println(".... reg cancel");
        dbSaveRequest(updateData, SignInStatus.SIGN_IN_NONE);

        if (updateData.getMessage().getCallbackData() != null) {
            Message message = updateData.getMessage();
            message.setMessageType(MessageType.EDIT_MESSAGE);
            message.setKeyboardType(null);
            message.setText(DefaultBotMessages.SIGN_UP_DENY.getMessage());

            exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
        } else {
            Message message = updateData.getMessage();
            message.setMessageType(MessageType.MESSAGE);
            message.setText(DefaultBotMessages.SIGN_UP_DENY.getMessage());

            exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
        }
    }

    private void dbSaveRequest(UpdateData updateData, SignInStatus signInStatus){
        updateData.getUserData().setSignInStatus(signInStatus);
        updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
        exchangeController.sendDataTo(ServiceNames.DATA_SERVICE, updateData);
    }

}

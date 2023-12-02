package com.zinoviev.orchestrator.service;

import com.zinoviev.entity.enums.*;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceURL;


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

    public void proceedSignUp(UpdateDto updateDto) {
        selectSignUpStage(updateDto);
    }


    private void selectSignUpStage(UpdateDto updateDto) {
        if (updateDto.getMessageDto().getCallbackData() != null && updateDto.getMessageDto().getCallbackData().equals(DENY)) {
            regCancel(updateDto);
            return;
        }

        switch (updateDto.getUserDTO().getSignInStatus()) {
            case SIGN_UP_NONE -> userSignUpShowOffer(updateDto);
            case SIGN_UP_OFFER -> userSignUpSelectNameType(updateDto);
            case SIGN_UP_SELECT_NAME_TYPE -> userSignUpNameTypeCallbackHandler(updateDto);
            case SIGN_UP_SELECT_NAME -> userSignUpAcceptNewName(updateDto);
        }
    }


    private void userSignUpShowOffer(UpdateDto updateDto) {
        dbSaveRequest(updateDto, SignInStatus.SIGN_UP_OFFER);

        messageBuilderService
                .setText(updateDto, DefaultBotMessages.SIGN_UP_OFFER.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Да, конечно", "Нет, спасибо"},
                        new String[]{ACCEPTED, DENY});

        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void userSignUpSelectNameType(UpdateDto updateDto) {
        MessageType messageType = MessageType.EDIT_MESSAGE;

        if (updateDto.getMessageDto().getCallbackData() == null) {
            messageType = MessageType.MESSAGE;
        }

        if (updateDto.getMessageDto().getCallbackData() != null) {
            if (updateDto.getMessageDto().getCallbackData().equals(ACCEPTED)) {
                dbSaveRequest(updateDto, SignInStatus.SIGN_UP_SELECT_NAME_TYPE);
                messageType = MessageType.EDIT_MESSAGE;
            }
        }

        messageBuilderService
                .setText(updateDto, DefaultBotMessages.SIGN_UP_SELECT_NAME_TYPE.getMessage())
                .setMessageType(updateDto, messageType)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Я его придумаю", updateDto.getMessageDto().getFirstName(), "Возможно позже"},
                        new String[]{NEW_NAME, USER_NAME, DENY}
                );

        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void userSignUpNameTypeCallbackHandler(UpdateDto updateDto) {
        if (updateDto.getMessageDto().getCallbackData() != null) {
            updateDto.setRequestType(RequestType.SAVE_ONLY);

            if (updateDto.getMessageDto().getCallbackData().equals(NEW_NAME)) {
                userSignUpNewNameSelected(updateDto);
            } else if (updateDto.getMessageDto().getCallbackData().equals(USER_NAME)) {
                userSignUpUserNameSelected(updateDto);
            } else {
                regCancel(updateDto);
            }
        } else {
            userSignUpSelectNameType(updateDto);
        }
    }

    private void userSignUpNewNameSelected(UpdateDto updateDto) {
        dbSaveRequest(updateDto, SignInStatus.SIGN_UP_SELECT_NAME);

        messageBuilderService
                .setText(updateDto, DefaultBotMessages.SIGN_UP_SELECT_NAME.getMessage())
                .setMessageType(updateDto, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.NULL);

        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void userSignUpUserNameSelected(UpdateDto updateDto) {
        updateDto.getUserDTO().setGameName(updateDto.getMessageDto().getFirstName());
        dbSaveRequest(updateDto, SignInStatus.SIGN_UP_COMPLETE);

        messageBuilderService
                .setText(updateDto, DefaultBotMessages.SIGN_UP_COMPLETE.getMessage())
                .setMessageType(updateDto, MessageType.DELETE);
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);

        messageBuilderService
                .setText(updateDto, DefaultBotMessages.SIGN_UP_COMPLETE.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.REPLY_ADD);
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void userSignUpAcceptNewName(UpdateDto updateDto) {
        updateDto.getUserDTO().setGameName(updateDto.getMessageDto().getText());
        dbSaveRequest(updateDto, SignInStatus.SIGN_UP_COMPLETE);

        messageBuilderService
                .setText(updateDto, DefaultBotMessages.SIGN_UP_COMPLETE.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.REPLY_ADD);
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void regCancel(UpdateDto updateDto) {
        SignInStatus signInStatus;
        String message;
        MessageType type;

        if (updateDto.getUserDTO().getGameName() == null) {
            signInStatus = SignInStatus.SIGN_UP_NONE;
            message = DefaultBotMessages.SIGN_UP_DENY.getMessage();
        } else {
            signInStatus = SignInStatus.SIGN_UP_COMPLETE;
            message = DefaultBotMessages.CHANGE_NAME_CANCEL.getMessage();
        }

        if (updateDto.getMessageDto().getCallbackData() == null) {
            type = MessageType.MESSAGE;
        } else {
            type = MessageType.EDIT_MESSAGE;
        }

        messageBuilderService
                .setText(updateDto, message)
                .setMessageType(updateDto, type);

        dbSaveRequest(updateDto, signInStatus);
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void dbSaveRequest(UpdateDto updateDto, SignInStatus signInStatus) {
        updateDto.getUserDTO().setSignInStatus(signInStatus);
        updateDto.setRequestType(RequestType.SAVE_ONLY);
        exchangeController.exchangeWith(ServiceURL.dataServiceUser, updateDto);
    }


}

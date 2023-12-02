package com.zinoviev.data.handler;

import com.zinoviev.data.controller.ExchangeController;
import com.zinoviev.data.service.UserRepositoryService;
import com.zinoviev.entity.dto.data.UserDto;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestHandlerService {

    private final UserRepositoryService userRepositoryService;

    private final ExchangeController exchangeController;


    @Autowired
    public RequestHandlerService(UserRepositoryService userRepositoryService, ExchangeController exchangeController) {
        this.userRepositoryService = userRepositoryService;

        this.exchangeController = exchangeController;
    }

    //TODO сделать разделение по типу запроса
    // Запросы могут быть типов:
    // только сохранение
    // создание квеста
    // запуск квеста
    // данные игрока


    /**
     * @param updateDto от телеграмма приходит RequestType = GET_USER_DATA
     *                  от аркестратора приходит RequestType = SAVE_ONLY и все остальные
     */
    public void processTheRequest(UpdateDto updateDto) {
        RequestType requestType = updateDto.getRequestType();
        System.out.println(requestType);

        switch (requestType) {
            case SAVE_ONLY -> {
                userRepositoryService.saveUser(updateDto.getUserDTO());
            }
            case GET_USER_DATA -> getUserData(updateDto);
        }
    }

    private void getUserData(UpdateDto updateDto) {
        System.out.println("Get data");
        UserDto userDto = userRepositoryService.getUserDataByTelegramId(updateDto);

        updateDto.setUserDTO(userDto);
        exchangeController.sendUserDataResponse(updateDto);
    }
}

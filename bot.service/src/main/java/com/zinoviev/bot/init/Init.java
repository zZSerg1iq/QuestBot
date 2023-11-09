package com.zinoviev.bot.init;

import com.zinoviev.bot.controller.BotDataController;
import com.zinoviev.bot.controller.TelegramController;
import com.zinoviev.bot.controller.impl.SimpleBotDataController;
import com.zinoviev.bot.message.handler.MessageBuilder;
import com.zinoviev.bot.message.handler.ResponseMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Init {

    private TelegramController telegramController;
    private ResponseMessageBuilder responseMessageBuilder;
    private BotDataController botDataController;

    @Autowired
    public Init(TelegramController telegramController) {
        this.telegramController = telegramController;

        responseMessageBuilder = new MessageBuilder(telegramController);
        botDataController = new SimpleBotDataController(responseMessageBuilder);
        telegramController.setBotDataController(botDataController);
    }

}

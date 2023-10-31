package com.zinoviev.questbot.OLD.bot;

import com.zinoviev.sandbox.bot.bot_dispatcher.QuestBotController;
import com.zinoviev.sandbox.bot.config.BotConfig;
import com.zinoviev.sandbox.bot.request.request_controller.RequestControllerFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j
public class QuestBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final RequestControllerFactory requestControllerFactory;


    @Autowired
    public QuestBot(BotConfig config, RequestControllerFactory requestControllerFactory) {
        this.config = config;
        this.requestControllerFactory = requestControllerFactory;
        requestControllerFactory.setBotController(new QuestBotController(this));
    }

    @Override
    public String getBotToken() {
        return config.getBOT_TOKEN();
    }

    @Override
    public String getBotUsername() {
        return config.getBOT_NAME();
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        log.debug(update.getMessage());
        requestControllerFactory.addRequest(update);
    }



}

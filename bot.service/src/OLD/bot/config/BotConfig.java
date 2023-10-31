package com.zinoviev.questbot.OLD.bot.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Data
public class BotConfig {

    @Value("${bot.name}")
    private String BOT_NAME;

    @Value("${bot.token}")
    private String BOT_TOKEN;
/*
    @Value("${questbot-salt}")
    private String salt;

    @Bean
    public CryptoTool getCryptoToll(){
        return new CryptoTool(salt);
    }*/

}

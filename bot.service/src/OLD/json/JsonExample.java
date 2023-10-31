package com.zinoviev.questbot.OLD.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinoviev.sandbox.bot.entity.models.user.BotUser;

public class JsonExample {

    public static void main(String[] args) throws Exception {
        // Создаем объект ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Создаем объект для сериализации в JSON
        BotUser user = new BotUser();

        // Преобразуем объект в JSON-строку
        String json = objectMapper.writeValueAsString(user);

        // Выводим JSON-строку
        System.out.println(json);
    }

    // Пример класса с данными пользователя
    static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Геттеры и сеттеры
        // ...
    }
}

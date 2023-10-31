package com.zinoviev.questbot.OLD.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class JsonGetter {
    public static void main(String[] args) throws Exception {
        // Создаем объект ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON-строка
        String json = "{\"name\":\"John Doe\",\"age\":30}";

        // Преобразуем JSON-строку в объект
        User user = objectMapper.readValue(json, User.class);

        // Выводим данные объекта
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
    }

    // Пример класса с данными пользователя
    @Getter
    @Setter
    static class User {
        private String name;
        private int age;

        // Геттеры и сеттеры
        // ...

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}

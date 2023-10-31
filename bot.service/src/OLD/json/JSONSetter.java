package com.zinoviev.questbot.OLD.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class JSONSetter {
    public static void main(String[] args) throws Exception {
        // Создаем объект ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON-строка
        String json = "{\"name\":\"John Doe\",\"age\":30}";

        // Преобразуем JSON-строку в объект
        User user = objectMapper.readValue(json, User.class);

        // Изменяем поле объекта
        user.setAge(31);

        // Преобразуем объект в JSON-строку
        String updatedJson = objectMapper.writeValueAsString(user);

        // Выводим обновленную JSON-строку
        System.out.println(updatedJson);
    }

    // Пример класса с данными пользователя
    @Getter
    @Setter
    static class User {
        private String name;

        private int age;

        public void setAge(int age) {
            this.age = age;
        }
    }
}

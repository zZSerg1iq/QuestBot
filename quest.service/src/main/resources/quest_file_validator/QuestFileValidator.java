package com.zinoviev.questbot.OLD.quest_file_validator;

import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;
import com.zinoviev.sandbox.quest_file_validator.data.QuestFilesManager;
import com.zinoviev.sandbox.quest_file_validator.data.qustParser.QuestParser;

import java.io.File;

public class QuestFileValidator {

    private final File questFile;

    public QuestFileValidator(File questFile) {
        this.questFile = questFile;
    }

    public String checkFile() {
        if (questFile.getName().contains(".json")) {
            return validateJSONQuestFile();
        } else {
            return validateTXTQuestFile();
        }
    }

    private String validateJSONQuestFile(){
        QuestFilesManager questFilesManager = new QuestFilesManager();
        try {
            BotQuest quest = questFilesManager.readQuestFile(questFile.getAbsolutePath());
        } catch (Exception e) {
            return "Ошибка чтения файла JSON. \nРекомендуется проверить файл в JSON валидаторе, например вот здесь:\n" +
                    "https://jsonlint.com/";
        }
        return "Файл квеста успешно прошел валидацию и был сохранен в списке ваших квестов.";
    }

    private String validateTXTQuestFile(){
        QuestParser questParser = new QuestParser(questFile.getAbsolutePath());

        BotQuest quest = null;
        try {
            quest = questParser.loadAndParseQuest();
        } catch (Exception e) {
            return "Ошибка чтения файла...\n"+e.getMessage();
        }

        // создаем JSON из текста
        QuestFilesManager questFilesManager = new QuestFilesManager();
        questFilesManager.buildQuestFile(quest, questFile.getName()+".json");
        return "Файл квеста успешно прошел валидацию и был сохранен в списке ваших квестов.";
    }
}

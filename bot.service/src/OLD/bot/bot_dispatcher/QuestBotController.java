package com.zinoviev.questbot.OLD.bot.bot_dispatcher;

import com.zinoviev.sandbox.bot.QuestBot;
import com.zinoviev.sandbox.quest_file_validator.QuestFileValidator;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class QuestBotController implements TelegramBotController {

    private final QuestBot questBot;

    public QuestBotController(QuestBot questBot) {
        this.questBot = questBot;
    }

    // эту логику перенести на сторону обработчика, но могут возникнуть ситуации,
    // когда появится сразу 2 обработчика от 1 пользователя, и они вместе будут слать сообщения вразнобой
    @Override
    public void sendMessageList(LinkedHashMap<Object, Integer> messages) {
        messages.forEach((message, integer) -> {
            String x = message.getClass().getName().substring(message.getClass().getName().lastIndexOf(".") + 1);
           // System.out.println("message type: " + x);
            try {
                switch (x) {
                    case "EditMessageText" -> questBot.execute((EditMessageText) message);
                    case "EditMessageReplyMarkup" -> questBot.execute((EditMessageReplyMarkup) message);
                    case "SendAnimation" -> questBot.execute((SendAnimation) message);
                    case "SendPhoto" -> questBot.execute((SendPhoto) message);
                    case "SendMediaGroup" -> questBot.execute((SendMediaGroup) message);
                    case "SendMessage" -> questBot.execute((SendMessage) message);
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void sendMessage(SendMessage message) {
        try {
            questBot.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendMessage");
        }
    }

    @Override
    public void sendMessage(EditMessageText editMessageText) {
        try {
            questBot.execute(editMessageText);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить EditMessageText");
        }
    }

    @Override
    public void sendMessage(EditMessageReplyMarkup replyMarkup) {
        try {
            questBot.execute(replyMarkup);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить ReplyMarkup");
        }
    }

    @Override
    public void sendMessage(SendAnimation sendAnimation) {
        try {
            questBot.execute(sendAnimation);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendAnimation");
        }
    }

    @Override
    public void sendMessage(SendPhoto sendPhoto) {
        try {
            questBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendPhoto");
        }
    }

    @Override
    public void sendMessage(SendMediaGroup sendMediaGroup) {
        try {
            questBot.execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendMediaGroup");
        }
    }

    public void downloadQuestFile(Update update){
        Document document = update.getMessage().getDocument();
        String fileId = document.getFileId();

        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(fileId);

        String messageText = "Что-то пошло не так. Не удалось скачать либо проверить файл квеста.";

        java.io.File downloadedFile = null;
        try {
            File file = questBot.execute(getFileMethod);
            java.io.File telegramFile = questBot.downloadFile(file);
            InputStream is = new FileInputStream(telegramFile);


            downloadedFile = new java.io.File(document.getFileName());
            System.out.println(downloadedFile.getPath());
            FileOutputStream fos = new FileOutputStream(downloadedFile);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            is.close();
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }

        if (downloadedFile != null) {
            if (downloadedFile.getName().contains(".json") || downloadedFile.getName().contains(".txt")) {
                messageText = new QuestFileValidator(downloadedFile).checkFile();
            } else messageText = "Неизвестное расширение файла.\nКвестовые файлы должна иметь расширение .txt или .json";
        }

        sendMessage(SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(messageText)
                .build());
    }


}

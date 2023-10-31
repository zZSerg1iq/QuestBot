package com.zinoviev.bot.controller.update;

import com.zinoviev.bot.config.Config;
import com.zinoviev.bot.controller.rest.out.BotRequestController;
import com.zinoviev.bot.entity.builder.UpdateDataMapper;
import com.zinoviev.bot.entity.rest.UpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class SimpleTelegramController extends TelegramLongPollingBot implements TelegramController {

    private final Config config;

    private final UpdateDataMapper updateDataMapper;

    private final BotRequestController requestController;

    @Autowired
    public SimpleTelegramController(Config config, UpdateDataMapper updateDataMapper, BotRequestController requestController) throws TelegramApiException {
        this.config = config;
        this.updateDataMapper = updateDataMapper;
        this.requestController = requestController;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
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
        UpdateData updateData = updateDataMapper.buildUpdateData(update);
        requestController.dbUserDataRequest(updateData);

        // log.debug(update.getMessage());
    }

    @Override
    public void saveUpdatedInfo(UpdateData updateData){
        requestController.dbUserDataRequest(updateData);
    }


    /*public void sendMessageList(LinkedHashMap<Object, Integer> messages) {
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
*/

    @Override
    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendMessage");
        }
    }

    @Override
    public void sendMessage(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить EditMessageText");
        }
    }

    @Override
    public void sendMessage(EditMessageReplyMarkup replyMarkup) {
        try {
            execute(replyMarkup);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить ReplyMarkup");
        }
    }

    @Override
    public void sendMessage(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить DeleteMessage");
        }
    }


    @Override
    public void sendMessage(SendAnimation sendAnimation) {
        try {
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendAnimation");
        }
    }

    @Override
    public void sendMessage(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendPhoto");
        }
    }

    @Override
    public void sendMessage(SendMediaGroup sendMediaGroup) {
        try {
            execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            System.out.println("не удалось выполнить SendMediaGroup");
        }
    }

    public void downloadQuestFile(Update update) {
       /* Document document = update.getMessage().getDocument();
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
                .build());*/
    }

}

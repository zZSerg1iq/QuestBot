package com.zinoviev.bot.mapper;

import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.updatedata.entity.Document;
import com.zinoviev.entity.model.updatedata.entity.Location;
import com.zinoviev.entity.model.updatedata.entity.Message;
import com.zinoviev.entity.model.updatedata.entity.Photo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateDataMapper {

    public UpdateData mapUpdateToUpdateData(Update update) {
        UpdateData updateData = new UpdateData();
        updateData.setMessage(new Message());
        updateData.setUpdateId(update.getUpdateId());

        if (update.hasMessage()) {
            System.out.println("Has message");
            setUpdateDataMessageInfo(updateData, update);

            if (update.getMessage().hasLocation()) {
                System.out.println("Has Location");
                setUpdateDataLocationInfo(updateData, update);
            }

            if (update.getMessage().hasDocument()) {
                System.out.println("Has Document");
                setUpdateDataDocumentInfo(updateData, update);
            }
            if (update.getMessage().hasPhoto()) {
                System.out.println("Has Photo");
                setUpdateDataPhotoInfo(updateData, update);
            }
        }

        if (update.hasCallbackQuery()) {
            System.out.println("Has CallbackQuery");
            setUpdateDataCallbackQueryInfo(updateData, update);
        }

        return updateData;
    }

    private void setUpdateDataCallbackQueryInfo(UpdateData updateData, Update update) {
        updateData.setDate(update.getCallbackQuery().getMessage().getDate());

        updateData.getMessage().setCallbackData(update.getCallbackQuery().getData());
        updateData.getMessage().setCallbackMessageId(update.getCallbackQuery().getMessage().getMessageId());
        updateData.getMessage().setChatId(update.getCallbackQuery().getMessage().getChatId());

        updateData.getMessage().setUserId(update.getCallbackQuery().getFrom().getId());
        updateData.getMessage().setFirstName(update.getCallbackQuery().getFrom().getFirstName());
    }

    private void setUpdateDataMessageInfo(UpdateData updateData, Update update) {
        updateData.setDate(update.getMessage().getDate());

        updateData.getMessage().setMessageId(update.getMessage().getMessageId());

        updateData.getMessage().setUserId(update.getMessage().getFrom().getId());
        updateData.getMessage().setFirstName(update.getMessage().getFrom().getFirstName());
        updateData.getMessage().setLastName(update.getMessage().getFrom().getLastName());
        updateData.getMessage().setUserName(update.getMessage().getFrom().getUserName());

        updateData.getMessage().setChatId(update.getMessage().getChatId());
        updateData.getMessage().setText(update.getMessage().getText());
    }

    private void setUpdateDataLocationInfo(UpdateData updateData, Update update) {
        Location location = new Location();
        location.setLatitude(update.getMessage().getLocation().getLatitude());
        location.setLongitude(update.getMessage().getLocation().getLongitude());
        location.setHeading(update.getMessage().getLocation().getHeading());
        location.setProximityAlertRadius(update.getMessage().getLocation().getProximityAlertRadius());

        updateData.setLocation(location);
    }

    private void setUpdateDataDocumentInfo(UpdateData updateData, Update update) {
        Document document = new Document();
        document.setFileId(update.getMessage().getDocument().getFileId());
        document.setFileUniqueId(update.getMessage().getDocument().getFileUniqueId());
        document.setFileName(update.getMessage().getDocument().getFileName());
        document.setFileSize(update.getMessage().getDocument().getFileSize());

        updateData.setDocument(document);
    }

    private void setUpdateDataPhotoInfo(UpdateData updateData, Update update) {
        List<Photo> photos = new ArrayList<>();
        List<PhotoSize> updatePhotos = update.getMessage().getPhoto();

        for (PhotoSize ps : updatePhotos) {
            Photo photo = new Photo();

            photo.setFileId(ps.getFileId());
            photo.setFileUniqueId(ps.getFileUniqueId());
            photo.setWidth(ps.getWidth());
            photo.setHeight(ps.getHeight());
            photo.setFileSize(ps.getFileSize());
            photo.setFilePath(ps.getFilePath());
            photos.add(photo);
        }

        updateData.setPhotoList(photos);
    }


}

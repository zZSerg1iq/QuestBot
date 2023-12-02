package com.zinoviev.bot.mapper;

import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.dto.update.include.DocumentDto;
import com.zinoviev.entity.dto.update.include.LocationDto;
import com.zinoviev.entity.dto.update.include.MessageDto;
import com.zinoviev.entity.dto.update.include.PhotoDto;
import com.zinoviev.entity.enums.RequestType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateDataMapper {

    public UpdateDto mapUpdateToUpdateData(Update update) {
        UpdateDto updateDto = new UpdateDto();
        updateDto.setRequestType(RequestType.GET_USER_DATA);
        updateDto.setMessageDto(new MessageDto());
        updateDto.setUpdateId(update.getUpdateId());

        if (update.hasMessage()) {
            System.out.println("Has message");
            setUpdateDataMessageInfo(updateDto, update);

            if (update.getMessage().hasLocation()) {
                System.out.println("Has Location");
                setUpdateDataLocationInfo(updateDto, update);
            }

            if (update.getMessage().hasDocument()) {
                System.out.println("Has Document");
                setUpdateDataDocumentInfo(updateDto, update);
            }
            if (update.getMessage().hasPhoto()) {
                System.out.println("Has Photo");
                setUpdateDataPhotoInfo(updateDto, update);
            }
        }

        if (update.hasCallbackQuery()) {
            System.out.println("Has CallbackQuery");
            setUpdateDataCallbackQueryInfo(updateDto, update);
        }

        return updateDto;
    }

    private void setUpdateDataCallbackQueryInfo(UpdateDto updateDto, Update update) {
        updateDto.setDate(update.getCallbackQuery().getMessage().getDate());

        updateDto.getMessageDto().setCallbackData(update.getCallbackQuery().getData());
        updateDto.getMessageDto().setCallbackMessageId(update.getCallbackQuery().getMessage().getMessageId());
        updateDto.getMessageDto().setChatId(update.getCallbackQuery().getMessage().getChatId());

        updateDto.getMessageDto().setUserId(update.getCallbackQuery().getFrom().getId());
        updateDto.getMessageDto().setFirstName(update.getCallbackQuery().getFrom().getFirstName());
    }

    private void setUpdateDataMessageInfo(UpdateDto updateDto, Update update) {
        updateDto.setDate(update.getMessage().getDate());

        updateDto.getMessageDto().setMessageId(update.getMessage().getMessageId());

        updateDto.getMessageDto().setUserId(update.getMessage().getFrom().getId());
        updateDto.getMessageDto().setFirstName(update.getMessage().getFrom().getFirstName());
        updateDto.getMessageDto().setLastName(update.getMessage().getFrom().getLastName());
        updateDto.getMessageDto().setUserName(update.getMessage().getFrom().getUserName());

        updateDto.getMessageDto().setChatId(update.getMessage().getChatId());
        updateDto.getMessageDto().setText(update.getMessage().getText());
    }

    private void setUpdateDataLocationInfo(UpdateDto updateDto, Update update) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLatitude(update.getMessage().getLocation().getLatitude());
        locationDto.setLongitude(update.getMessage().getLocation().getLongitude());
        locationDto.setHeading(update.getMessage().getLocation().getHeading());
        locationDto.setProximityAlertRadius(update.getMessage().getLocation().getProximityAlertRadius());

        updateDto.setLocationDto(locationDto);
    }

    private void setUpdateDataDocumentInfo(UpdateDto updateDto, Update update) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setFileId(update.getMessage().getDocument().getFileId());
        documentDto.setFileUniqueId(update.getMessage().getDocument().getFileUniqueId());
        documentDto.setFileName(update.getMessage().getDocument().getFileName());
        documentDto.setFileSize(update.getMessage().getDocument().getFileSize());

        updateDto.setDocumentDto(documentDto);
    }

    private void setUpdateDataPhotoInfo(UpdateDto updateDto, Update update) {
        List<PhotoDto> photoDtos = new ArrayList<>();
        List<PhotoSize> updatePhotos = update.getMessage().getPhoto();

        for (PhotoSize ps : updatePhotos) {
            PhotoDto photoDto = new PhotoDto();

            photoDto.setFileId(ps.getFileId());
            photoDto.setFileUniqueId(ps.getFileUniqueId());
            photoDto.setWidth(ps.getWidth());
            photoDto.setHeight(ps.getHeight());
            photoDto.setFileSize(ps.getFileSize());
            photoDto.setFilePath(ps.getFilePath());
            photoDtos.add(photoDto);
        }

        updateDto.setPhotoDtoList(photoDtos);
    }


}

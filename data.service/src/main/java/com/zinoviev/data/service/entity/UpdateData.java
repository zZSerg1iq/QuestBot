package com.zinoviev.data.service.entity;

import com.zinoviev.data.entity.RequestStatus;
import com.zinoviev.data.service.entity.updatedata.entity.Document;
import com.zinoviev.data.service.entity.updatedata.entity.Location;
import com.zinoviev.data.service.entity.updatedata.entity.Photo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UpdateData {

    private RequestStatus requestStatus;

    //user
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;


    //chat
    private Integer updateId;
    private Integer messageId;
    private Long chatId;
    private Integer date;
    private String text;

    private Location location;
    private Document document;
    private List<Photo> photoList;

    private Boolean successfulPayment;

    private String callbackQueryData;
    private Integer callbackQueryMessageId;


   // private boolean hasEditedMessage;
   // private String editedMessage;

    private UserData userData;

}

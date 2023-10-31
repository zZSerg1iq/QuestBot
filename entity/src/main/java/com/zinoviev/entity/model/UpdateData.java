package com.zinoviev.entity.model;

import com.zinoviev.entity.enums.RequestStatus;
import com.zinoviev.entity.model.updatedata.entity.Document;
import com.zinoviev.entity.model.updatedata.entity.Location;
import com.zinoviev.entity.model.updatedata.entity.Photo;
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

package com.zinoviev.entity.model;

import com.zinoviev.entity.enums.RequestStatus;
import com.zinoviev.entity.model.updatedata.entity.*;
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

    private Integer updateId;
    private Integer date;

    private RequestStatus requestStatus;
    private Message message;
    private Location location;
    private Document document;
    private List<Photo> photoList;

    private UserData userData;

}

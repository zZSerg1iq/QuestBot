package com.zinoviev.entity.dto.update;

import com.zinoviev.entity.dto.data.UserDto;
import com.zinoviev.entity.dto.update.include.LocationDto;
import com.zinoviev.entity.enums.RequestType;
import com.zinoviev.entity.dto.update.include.DocumentDto;
import com.zinoviev.entity.dto.update.include.MessageDto;
import com.zinoviev.entity.dto.update.include.PhotoDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UpdateDto {

    private Integer updateId;
    private Integer date;

    private RequestType requestType;
    private MessageDto messageDto;
    private LocationDto locationDto;
    private DocumentDto documentDto;
    private List<PhotoDto> photoDtoList;

    private UserDto userDTO;

}

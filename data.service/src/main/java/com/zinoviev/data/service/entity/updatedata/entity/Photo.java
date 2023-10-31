package com.zinoviev.data.service.entity.updatedata.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Photo {

    private String fileId;
    private String fileUniqueId;
    private Integer width;
    private Integer height;
    private Integer fileSize;
    private String filePath;

}

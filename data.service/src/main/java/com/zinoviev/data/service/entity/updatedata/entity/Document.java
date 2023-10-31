package com.zinoviev.data.service.entity.updatedata.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Document {

    private String fileId;

    private String fileUniqueId;

    private String fileName;

    private Long fileSize;

}

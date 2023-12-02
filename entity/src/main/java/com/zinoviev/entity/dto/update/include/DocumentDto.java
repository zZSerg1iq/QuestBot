package com.zinoviev.entity.dto.update.include;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class DocumentDto {

    private String fileId;
    private String fileUniqueId;
    private String fileName;
    private Long fileSize;

}

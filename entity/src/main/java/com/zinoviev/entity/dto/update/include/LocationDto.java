package com.zinoviev.entity.dto.update.include;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class LocationDto {

    private Double longitude;
    private Double latitude;
    private Integer heading;
    private Integer proximityAlertRadius;

}

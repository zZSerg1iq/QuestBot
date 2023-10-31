package com.zinoviev.data.service.entity.updatedata.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Location {

    private Double longitude;

    private Double latitude;

    private Integer heading;

    private Integer proximityAlertRadius;

}

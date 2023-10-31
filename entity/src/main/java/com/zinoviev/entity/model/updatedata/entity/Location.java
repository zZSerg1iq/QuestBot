package com.zinoviev.entity.model.updatedata.entity;


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

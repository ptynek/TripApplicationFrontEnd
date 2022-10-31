package com.tripapplicationf.tripapplicationf.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitiesDto {

    private long id;
    private String city;
    private String country;
    private double latitude;
    private double longitude;

}

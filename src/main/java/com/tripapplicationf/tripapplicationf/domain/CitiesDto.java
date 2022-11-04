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
    private boolean active;

    public CitiesDto(String city, String country, double latitude, double longitude) {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

package com.tripapplicationf.tripapplicationf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    private long id;
    private double temperature;
    private double windspeed;
    private int weathercode;
}

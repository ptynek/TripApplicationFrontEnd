package com.tripapplicationf.tripapplicationf.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCodeDto {

    private long id;
    private int weatherCode;
    private String description;
}

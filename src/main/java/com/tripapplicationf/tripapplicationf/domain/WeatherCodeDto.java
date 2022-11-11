package com.tripapplicationf.tripapplicationf.domain;

import com.vaadin.flow.shared.Registration;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCodeDto {

    private long id;
    private int weatherCode;
    private String description;

    public WeatherCodeDto(int weatherCode, String description) {
        this.weatherCode = weatherCode;
        this.description = description;
    }

    public WeatherCodeDto(Registration addValueChangeListener, String description) {

    }
}

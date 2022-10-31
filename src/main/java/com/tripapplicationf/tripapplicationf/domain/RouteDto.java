package com.tripapplicationf.tripapplicationf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDto {

    private long id;
    private long lengthInMeters;
    private long travelTimeInSeconds;
    private long trafiicDelayInSeconds;
    private CitiesDto cityFrom;
    private CitiesDto cityTo;

    private PassengersDto passengersDto;

    public RouteDto(long id, long lengthInMeters, long travelTimeInSeconds, long trafiicDelayInSeconds, CitiesDto cityFrom, CitiesDto cityTo) {
        this.id = id;
        this.lengthInMeters = lengthInMeters;
        this.travelTimeInSeconds = travelTimeInSeconds;
        this.trafiicDelayInSeconds = trafiicDelayInSeconds;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
    }

}

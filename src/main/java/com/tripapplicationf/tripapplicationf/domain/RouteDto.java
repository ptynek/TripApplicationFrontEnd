package com.tripapplicationf.tripapplicationf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDto {

    private long id;
    private double lengthInMeters;
    private long travelTimeInSeconds;
    private long trafiicDelayInSeconds;
    private CitiesDto cityFrom;
    private CitiesDto cityTo;

    private PassengersDto passengersDto;
    private LocalDateTime dateOfTrip;
    private BigDecimal price;


    public String getStringDateOfTrip(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return dateOfTrip.format(formatter);
    }

    public String getCityFrom() {
        return cityFrom.getCity();
    }

    public String getCityTo() {
        return cityTo.getCity();
    }

    public BigDecimal getLengthInKilometers() {
        BigDecimal length = new BigDecimal(lengthInMeters/1000).setScale(2, RoundingMode.HALF_UP);
        return length;
    }

    public String getTravelTime(){
        return LocalTime.MIN.plusSeconds(travelTimeInSeconds).toString();
    }

    public String getDelayTime(){
        return LocalTime.MIN.plusSeconds(trafiicDelayInSeconds).toString();
    }

    public String getPassenger(){
        return passengersDto.getId() + "/ " +
                passengersDto.getFirstName() + " " + passengersDto.getLastName();
    }

}

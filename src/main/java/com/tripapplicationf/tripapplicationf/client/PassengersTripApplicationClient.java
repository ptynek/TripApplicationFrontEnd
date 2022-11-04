package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.PassengersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PassengersTripApplicationClient {

    private final RestTemplate restTemplate;
    private final static String ENDPOINT_ADRESS = "http://localhost:8080/v1/";

    public List<PassengersDto> getPassengers() {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT_ADRESS + "passengers")
                .build()
                .encode()
                .toUri();

        PassengersDto[] passengersDto = restTemplate.getForObject(
                url,
                PassengersDto[].class
        );

        return Optional.ofNullable(passengersDto)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public PassengersDto getPassenger(final long passengerId){
        PassengersDto passenger = restTemplate.getForObject(
                ENDPOINT_ADRESS + "passengers/" + passengerId,
                PassengersDto.class,
                passengerId
        );

        return passenger;
    }

    public void changePassengerStatus(final long passengerId){
        if (getPassenger(passengerId).isActive()) {
            restTemplate.delete(ENDPOINT_ADRESS + "passengers/" + passengerId);
        } else {
            PassengersDto passenger = getPassenger(passengerId);
            log.info(String.valueOf(passenger));
            passenger.setActive(true);
            restTemplate.put(ENDPOINT_ADRESS + "passengers", passenger);
        }
    }

    public void createPassenger(final PassengersDto passengersDto){
        restTemplate.postForObject(
                ENDPOINT_ADRESS + "passengers",
                passengersDto,
                PassengersDto.class
        );
    }
}

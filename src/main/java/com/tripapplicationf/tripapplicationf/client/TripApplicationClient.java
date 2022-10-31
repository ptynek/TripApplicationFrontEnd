package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class TripApplicationClient {

    @Value("${app.api.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public List<CitiesDto> getCitiesDto(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/cities")
                .build()
                .encode()
                .toUri();

        CitiesDto[] citiesResponse = restTemplate.getForObject(url, CitiesDto[].class);

        log.info("VALUE: " + String.valueOf(citiesResponse));

        return Optional.ofNullable(citiesResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

}

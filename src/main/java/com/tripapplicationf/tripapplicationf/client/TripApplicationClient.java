package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.client.domain.CitiesList;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TripApplicationClient {

    private final RestTemplate restTemplate;


/*    public List<CitiesDto> getCitiesDto(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/cities")
                .build()
                .encode()
                .toUri();

       List<CitiesDto> citiesResponse = Collections.singletonList(restTemplate.getForObject(url, CitiesDto.class));

        return citiesResponse;
    }*/

    public List<CitiesDto> getCitiesDto(){

        URI url = UriComponentsBuilder.fromHttpUrl("https://trip-application.herokuapp.com/v1/cities")
                .build()
                .encode()
                .toUri();

        CitiesDto[] citiesResponse = restTemplate.getForObject(url, CitiesDto[].class);

        return Optional.ofNullable(citiesResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

}

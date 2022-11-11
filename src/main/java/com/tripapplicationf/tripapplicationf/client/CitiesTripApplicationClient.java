package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CitiesTripApplicationClient {

    @Value("${app.api.endpoint}")
    private String ENDPOINT_ADDRESS;

    private final RestTemplate restTemplate;

    public List<CitiesDto> getCitiesDto(){

        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT_ADDRESS + "cities")
                .build()
                .encode()
                .toUri();

        CitiesDto[] citiesResponse = restTemplate.getForObject(url, CitiesDto[].class);

        return Optional.ofNullable(citiesResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public CitiesDto getCity(final long cityId){
        CitiesDto city = restTemplate.getForObject(ENDPOINT_ADDRESS + "cities/" + cityId, CitiesDto.class, cityId);
        return city;
    }

    public void changeStatusOfCity(final long id){
        if (getCity(id).isActive()){
            restTemplate.delete( ENDPOINT_ADDRESS + "cities/" + id);
        } else {
            CitiesDto city = getCity(id);
            city.setActive(true);
            restTemplate.put(ENDPOINT_ADDRESS + "cities/", city);
        }
    }

    public void addCity(final CitiesDto city){

        restTemplate.postForObject(ENDPOINT_ADDRESS + "cities", city, CitiesDto.class);

    }



}

package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.tripapplicationf.tripapplicationf.domain.WeatherDto;
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
public class CitiesTripApplicationClient {

    @Value("${app.api.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public List<CitiesDto> getCitiesDto(){

        URI url = UriComponentsBuilder.fromHttpUrl(endpoint  + "/cities")
                .build()
                .encode()
                .toUri();

        CitiesDto[] citiesResponse = restTemplate.getForObject(url, CitiesDto[].class);

        return Optional.ofNullable(citiesResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public CitiesDto getCity(final long cityId){
        CitiesDto city = restTemplate.getForObject(endpoint + "/cities/" + cityId, CitiesDto.class, cityId);
        return city;
    }

    public void changeStatusOfCity(final long id){
        if (getCity(id).isActive()){
            restTemplate.delete( endpoint + "/cities/" + id);
        } else {
            CitiesDto city = getCity(id);
            city.setActive(true);
            restTemplate.put(endpoint + "/cities/", city);
        }
    }

    public void addCity(final CitiesDto city){

        restTemplate.postForObject(endpoint + "/cities", city, CitiesDto.class);

    }



}

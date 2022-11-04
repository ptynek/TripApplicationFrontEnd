package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WeatherTripApplicationClient {

    private final RestTemplate restTemplate;
    private final static String ENDPOINT_ADRESS = "http://localhost:8080/v1/";


    public WeatherDto getWeatherInCityBase(final double lat, final double lon){

        WeatherDto weatherDto = restTemplate.getForObject(ENDPOINT_ADRESS + "weather?lat={lat}&lon={lon}", WeatherDto.class, lat, lon);

        return weatherDto;
    }

    public WeatherDto checkWeatherInChoosenCity(final long idCity) {
        WeatherDto weather = restTemplate.getForObject(ENDPOINT_ADRESS + "", WeatherDto.class, idCity);


        return weather;
    }
}
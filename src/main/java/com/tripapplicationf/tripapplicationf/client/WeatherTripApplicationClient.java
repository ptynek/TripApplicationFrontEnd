package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WeatherTripApplicationClient {

    private final RestTemplate restTemplate;
    @Value("${app.api.endpoint}")
    private String ENDPOINT_ADRESS;


    public WeatherDto getWeatherInCityBase(final double lat, final double lon){

        WeatherDto weatherDto = restTemplate.getForObject(ENDPOINT_ADRESS + "weather?lat={lat}&lon={lon}", WeatherDto.class, lat, lon);

        return weatherDto;
    }

    public WeatherDto checkWeatherInChosenCity(final long idCity) {

        WeatherDto weather = restTemplate.getForObject(ENDPOINT_ADRESS + "weather/" + idCity, WeatherDto.class, idCity);

        return weather;
    }
}

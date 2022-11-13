package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.WeatherCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class WeatherCodesTripApplicationClient {

    private final RestTemplate restTemplate;
    @Value("${app.api.endpoint}")
    private String ENDPOINT_ADRESS;

    public List<WeatherCodeDto> getWeatherCodes(){
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT_ADRESS + "weather_codes")
                .build()
                .encode()
                .toUri();

        WeatherCodeDto[] weathercodesList = restTemplate.getForObject(
                url,
                WeatherCodeDto[].class
        );
        return Optional.ofNullable(weathercodesList)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public void addWeatherCode(final WeatherCodeDto weatherCodeDto){
        restTemplate.postForObject(
                ENDPOINT_ADRESS + "weather_codes",
                weatherCodeDto,
                WeatherCodeDto.class
        );
    }
}

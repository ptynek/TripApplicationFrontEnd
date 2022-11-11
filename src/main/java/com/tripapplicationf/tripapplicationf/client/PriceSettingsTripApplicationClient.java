package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.PriceSettingsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceSettingsTripApplicationClient {

    private final RestTemplate restTemplate;

    @Value("${app.api.endpoint}")
    private String ENDPOINT_ADDRESS;

    public void postPriceSettings(final BigDecimal price){
        restTemplate.exchange(
                ENDPOINT_ADDRESS + "priceSettings?pricePerKm=" + price,
                HttpMethod.POST,
                null,
                PriceSettingsDto.class
        );
    }

    public List<PriceSettingsDto> getAllPriceSettings(){
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT_ADDRESS + "priceSettings")
                .build()
                .encode()
                .toUri();

        PriceSettingsDto[] priceSettingsDtos = restTemplate.getForObject(
                url,
                PriceSettingsDto[].class
        );

        return Optional.ofNullable(priceSettingsDtos)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public BigDecimal getPricePerKm(){

        List<PriceSettingsDto> priceList = getAllPriceSettings();

        LocalDateTime maxDate = priceList.stream()
                .map(PriceSettingsDto::getDateOfModification)
                .max(LocalDateTime::compareTo)
                .get();

        return priceList.stream()
                .filter(priceSettingsDto -> maxDate.equals(priceSettingsDto.getDateOfModification()))
                .findFirst()
                .get().getPricePerKilometer();


    }
}

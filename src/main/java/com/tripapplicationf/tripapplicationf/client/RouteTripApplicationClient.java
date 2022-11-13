package com.tripapplicationf.tripapplicationf.client;

import com.tripapplicationf.tripapplicationf.domain.RouteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RouteTripApplicationClient {

    private final RestTemplate restTemplate;

    @Value("${app.api.endpoint}")
    private String ENDPOINT_ADDRESS;

    public List<RouteDto> getRoutes(){
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT_ADDRESS + "route")
                .build()
                .encode()
                .toUri();

        RouteDto[] routesList = restTemplate.getForObject(
                url,
                RouteDto[].class
        );

        return Optional.ofNullable(routesList)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public RouteDto calculateRoute(final long departureCityId,
                                   final long arrivalCityId){

        RouteDto route = restTemplate.getForObject(
                ENDPOINT_ADDRESS + "route?idCityFrom=" +
                        departureCityId + "&idCityTo=" + arrivalCityId,
                RouteDto.class,
                departureCityId,
                arrivalCityId
        );

        return route;
    }

    public void postRoute(final long departureCityId,
                          final long arrivalCityId,
                          final LocalDateTime dateTimeOfTrip){

        restTemplate.exchange(
                ENDPOINT_ADDRESS + "route?idCityFrom=" +
                        departureCityId + "&idCityTo=" + arrivalCityId +
                        "&dateTime=" + dateTimeOfTrip,
                HttpMethod.POST,
                null,
                RouteDto.class
        );
    }


}

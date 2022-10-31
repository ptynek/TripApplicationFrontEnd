package com.tripapplicationf.tripapplicationf.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CitiesList {

    private List<CitiesDto> citiesDtoList;
}

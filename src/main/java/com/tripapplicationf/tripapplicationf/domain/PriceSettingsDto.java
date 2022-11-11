package com.tripapplicationf.tripapplicationf.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceSettingsDto {

    private long id;
    private LocalDateTime dateOfModification;
    private BigDecimal pricePerKilometer;

}

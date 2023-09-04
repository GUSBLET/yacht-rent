package com.yachtrent.domain.DTO;

import lombok.Data;

@Data
public class PriceCalculationRequestDTO {
    private String dateOfStart;
    private String dateOfFinish;
    private String hourOfStart;
    private String hourOfFinish;
}

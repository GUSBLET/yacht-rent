package com.yachtrent.main.home.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilterDto {
    private int max;
    private int min;
    private boolean firstCheckboxForCapacity;
    private boolean secondCheckboxForCapacity;
    private boolean thirdCheckboxForCapacity;
    private boolean smallBoatSelect;
    private boolean boatSelect;
    private boolean shipSelect;
    private String capacity;
    private String yachtTypes;
}

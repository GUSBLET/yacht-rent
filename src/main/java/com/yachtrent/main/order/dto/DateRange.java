package com.yachtrent.main.order.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DateRange {
    private String startDate;
    private String endDate;
}

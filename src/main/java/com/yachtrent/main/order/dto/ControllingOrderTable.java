package com.yachtrent.main.order.dto;

import java.util.Date;

public record ControllingOrderTable (
        long id,
        String customerName,
        Date startTime,
        Date finishTime,
        float price,
        boolean isConfirmed) { }

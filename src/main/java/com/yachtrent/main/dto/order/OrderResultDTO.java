package com.yachtrent.main.dto.order;

import com.yachtrent.main.order.Order;

public record OrderResultDTO(
        String message,
        Order order) {}

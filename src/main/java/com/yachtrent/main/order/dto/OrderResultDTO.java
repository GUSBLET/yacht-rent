package com.yachtrent.main.order.dto;

import com.yachtrent.main.order.Order;

public record OrderResultDTO(
        String message,
        Order order) {}

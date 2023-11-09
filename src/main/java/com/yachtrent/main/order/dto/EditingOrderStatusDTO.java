package com.yachtrent.main.order.dto;

import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EditingOrderStatusDTO implements Mapper<EditingOrderStatusDTO, Order> {

    private long id;
    private OrderStatus orderStatus;

    @Override
    public EditingOrderStatusDTO toDto(Order entity) {
        return EditingOrderStatusDTO.builder()
                .id(entity.getId())
                .orderStatus(getOrderStatus())
                .build();
    }

    @Override
    public Order toEntity(EditingOrderStatusDTO dto) {
        return Order.builder()
                .id(dto.getId())
                .status(dto.getOrderStatus().toString())
                .build();
    }
}

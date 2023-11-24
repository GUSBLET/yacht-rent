package com.yachtrent.main.yacht.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.creator.Creator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YachtDto implements Mapper<YachtDto, Yacht> {

    private Long id;

    @NotBlank(message = "Enter yacht name")
    private String name;

    @Positive(message = "Enter age of yacht")
    private short age;

    @Positive(message = "Enter count of crew in the yacht")
    private short crew;

    @Positive(message = "Enter max capacity of passenger capacity on the yacht")
    private short capacity;

    @Positive(message = "Enter price per hour, that you for rent")
    private float pricePerHour;

    @Positive(message = "Enter length of yacht")
    private float length;

    @Positive(message = "Enter width of yacht")
    private float width;

    private String description;

    private Account account;

    @NotNull(message = "Choose type of yacht")
    private String type;

    private Creator creator;

    @Override
    public YachtDto toDto(Yacht entity) {
        return YachtDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .crew(entity.getCrew())
                .capacity(entity.getCapacity())
                .width(entity.getWidth())
                .length(entity.getLength())
                .description(entity.getDescription())
                .pricePerHour(entity.getPricePerHour())
                .creator(entity.getCreator())
                .account(entity.getAccount())
                .type(entity.getYachtType().getType())
                .build();
    }

    @Override
    public Yacht toEntity(YachtDto dto) {
        return Yacht.builder()
                .id(dto.id)
                .name(dto.name)
                .age(dto.age)
                .length(dto.length)
                .width(dto.width)
                .crew(dto.crew)
                .capacity(dto.capacity)
                .pricePerHour(dto.pricePerHour)
                .description(dto.description)
                .account(dto.account)
                .creator(dto.creator)
                .build();
    }
}

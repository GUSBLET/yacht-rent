package com.yachtrent.main.yacht.dto;


import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.yacht.Yacht;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RemoveYachtDTO implements Mapper<RemoveYachtDTO, Yacht> {

    private long id;

    @Override
    public RemoveYachtDTO toDto(Yacht entity) {
        return RemoveYachtDTO.builder().id(entity.getId()).build();
    }

    @Override
    public Yacht toEntity(RemoveYachtDTO dto) {
        return Yacht.builder().id(dto.getId()).build();
    }
}

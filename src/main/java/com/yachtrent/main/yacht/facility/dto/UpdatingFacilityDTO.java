package com.yachtrent.main.yacht.facility.dto;

import com.yachtrent.main.mapper.Mapper;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.facility.Facility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatingFacilityDTO implements Mapper<UpdatingFacilityDTO, Facility> {
    private String name;
    private short count;
    private Yacht yacht;
    @Override
    public UpdatingFacilityDTO toDto(Facility entity) {
        return UpdatingFacilityDTO.builder()
                .name(entity.getName())
                .count(entity.getCount())
                .yacht(entity.getYacht())
                .build();
    }

    @Override
    public Facility toEntity(UpdatingFacilityDTO dto) {
        return Facility.builder()
                .name(dto.getName())
                .count(dto.getCount())
                .yacht(dto.getYacht())
                .build();
    }
}

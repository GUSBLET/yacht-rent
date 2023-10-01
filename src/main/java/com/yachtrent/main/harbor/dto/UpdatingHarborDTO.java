package com.yachtrent.main.harbor.dto;

import com.yachtrent.main.harbor.Harbor;
import com.yachtrent.main.techniacal.mapper.Mapper;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatingHarborDTO implements Mapper<UpdatingHarborDTO, Harbor> {
    private long yachtId;

    private String name;

    private String city;

    private String address;

    private float longitude;

    private float latitude;

    @Override
    public UpdatingHarborDTO toDto(Harbor entity) {
        return UpdatingHarborDTO.builder()
                .name(entity.getName())
                .city(entity.getCity())
                .address(entity.getAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }

    @Override
    public Harbor toEntity(UpdatingHarborDTO dto) {
        return Harbor.builder()
                .city(dto.getCity())
                .name(dto.getName())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}

package com.yachtrent.main.yacht.creator;

import com.yachtrent.main.techniacal.mapper.Mapper;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatingCreatorDTO implements Mapper<CreatingCreatorDTO, Creator> {

    private String companyName;

    private String phone;

    private String mail;
    @Override
    public CreatingCreatorDTO toDto(Creator entity) {
        return CreatingCreatorDTO.builder()
                .companyName(entity.getCompanyName())
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .build();
    }

    @Override
    public Creator toEntity(CreatingCreatorDTO dto) {
        return Creator.builder()
                .companyName(dto.getCompanyName())
                .phone(dto.getPhone())
                .mail(dto.getMail())
                .build();
    }
}

package com.yachtrent.main.yacht.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.harbor.Harbor;
import com.yachtrent.main.mapper.Mapper;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.facility.Facility;
import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.type.Types;
import com.yachtrent.main.yacht.type.YachtType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.spi.Limit;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingYachtDTO implements Mapper<CreatingYachtDTO, Yacht> {

    private String name;

    private short age;

    private short crew;

    private short capacity;

    private float pricePerHour;

    private float length;

    private float width;

    private String description;

    private long accountId;

    private Types type;

    private Creator creator;

    private List<MultipartFile> photos;

    @Override
    public CreatingYachtDTO toDto(Yacht entity) {
        return CreatingYachtDTO.builder()
                .name(entity.getName())
                .age(entity.getAge())
                .crew(entity.getCrew())
                .capacity(entity.getCapacity())
                .width(entity.getWidth())
                .length(entity.getLength())
                .description(entity.getDescription())
                .pricePerHour(entity.getPrice_per_hour())
                .creator(entity.getCreator())
                .accountId(entity.getAccount().getId())
                .type(toYachtTypesDTO(entity.getYachtType().getType()))
                .photos(new ArrayList<>())
                .build();
    }

    @Override
    public Yacht toEntity(CreatingYachtDTO dto){

        return Yacht.builder()
                .name(dto.name)
                .age(dto.age)
                .length(dto.length)
                .width(dto.width)
                .crew(dto.crew)
                .capacity(dto.capacity)
                .price_per_hour(dto.pricePerHour)
                .description(dto.description)
                .photos(toYachtPhotos(dto.photos))
                .yachtType(YachtType.builder().type(dto.type.toString()).build())
                .creator(dto.creator)
                .account(Account.builder().id(dto.getAccountId()).build())
                .build();
    }

    private Types toYachtTypesDTO(String type){

        if (Objects.equals(type, Types.BOAT.toString())){
            return Types.BOAT;
        }
        else if(Objects.equals(type, Types.SMALL_BOAT.toString())){
            return Types.SMALL_BOAT;
        }
        else{
            return Types.SHIP;
        }
    }

    private List<YachtPhoto> toYachtPhotos(List<MultipartFile> photos) {
        List<YachtPhoto> result = new ArrayList<>();
        for (MultipartFile multipartFile: photos) {
            try {
                result.add(YachtPhoto.builder().photo(multipartFile.getBytes()).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}

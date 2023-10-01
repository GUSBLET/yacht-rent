package com.yachtrent.main.yacht.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.type.Types;
import com.yachtrent.main.yacht.type.YachtType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatingYachtDTO implements Mapper<CreatingYachtDTO, Yacht> {

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

    private long accountId;

    @NotNull(message = "Choose type of yacht")
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

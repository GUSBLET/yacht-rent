package com.yachtrent.main.yacht.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.type.Types;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YachtDTO implements Mapper<YachtDTO, Yacht> {

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
    private Types type;

    private Creator creator;

    //подумать про фотки
    private List<MultipartFile> photos;

    @Override
    public YachtDTO toDto(Yacht entity) {
        return YachtDTO.builder()
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
                .type(toYachtTypesDTO(entity.getYachtType().getType()))
                .build();
    }

    @Override
    public Yacht toEntity(YachtDTO dto) {
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

    /* тут надо обратить внимание на регистр
    не знаю учел ты это или нет

    реализация старой логики для метода:
      if (Objects.equals(type, Types.BOAT.toString())){
           ;
        }
        else if(Objects.equals(type, Types.SMALL_BOAT.toString())){
            return Types.SMALL_BOAT;
        }
        else{
            return Types.SHIP;
        } */
    private Types toYachtTypesDTO(String type) {
        switch (type.toLowerCase()) {
            case "boat" -> {
                return Types.BOAT;
            }
            case "small_boat" -> {
                return Types.SMALL_BOAT;
            }
            default -> {
                return Types.SHIP;
            }
        }
    }

    /* реализация старого метода:
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

    метод не тестировася */
    private List<YachtPhoto> toYachtPhotos(List<MultipartFile> photos) {
        return photos.stream().map(this::createYachtPhoto).toList();
    }

    /* этот метод должне быть в сервисе YachtPhoto
     * я считаю что этот медот нарушает уровень абстракции
     * */
    private YachtPhoto createYachtPhoto(MultipartFile photo) {
        try {
            return YachtPhoto.builder().photo(photo.getBytes()).build();
        } catch (IOException e) {
            log.error("Error in conversion method:YachtDTO/createYachtPhoto");
            throw new RuntimeException(e);
        }
    }
}

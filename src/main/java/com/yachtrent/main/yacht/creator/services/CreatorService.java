package com.yachtrent.main.yacht.creator.services;

import com.yachtrent.main.yacht.creator.CreatingCreatorDTO;
import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.creator.CreatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public Creator getByCreatorCompanyName(String companyName){
        return creatorRepository.findByCompanyName(companyName).orElseGet(() ->
        {
          return creatorRepository.findByCompanyName("ANONYMOUS").get();
        });
    }

    public ResponseEntity<String> createCreator(CreatingCreatorDTO dto){
        Optional<Creator> creator = Optional.ofNullable(
                creatorRepository.findByCompanyNameOrMailOrPhone(
                        dto.getCompanyName(),
                        dto.getMail(),
                        dto.getPhone()).orElseGet(() -> {
            return dto.toEntity(dto);
        }));
        if(creator.get().getId() == null){
            creatorRepository.save(creator.get());
            return ResponseEntity.ok().body("Creator added");
        }

        return ResponseEntity.badRequest().body("One of parameter has already used ");
    }

}

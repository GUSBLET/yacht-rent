package com.yachtrent.main.yacht.creator.services;

import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.creator.CreatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public Creator getCreatorBComapanyName(String comapanyName){
        return creatorRepository.findByСompanyName(comapanyName).orElseGet(() ->
        {
          return creatorRepository.findByСompanyName("ANONYMOUS").get();
        });
    }
}

package com.yachtrent.main.yacht.type.services;

import com.yachtrent.main.yacht.type.Types;
import com.yachtrent.main.yacht.type.YachtType;
import com.yachtrent.main.yacht.type.YachtTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class YachtTypeService {

    private YachtTypeRepository yachtTypeRepository;

    public YachtType getType(Types type){
        return yachtTypeRepository.findByType(type.toString()).orElseGet(() -> {
            return yachtTypeRepository.findByType("BOAT").get();
        });
    }
}

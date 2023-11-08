package com.yachtrent.main.yacht.type.services;

import com.yachtrent.main.yacht.type.Types;
import com.yachtrent.main.yacht.type.YachtType;
import com.yachtrent.main.yacht.type.YachtTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class YachtTypeService {
    private final YachtTypeRepository yachtTypeRepository;

    public YachtType findBYachtType(Types type) {
        return yachtTypeRepository.findByType(type.toString()).orElseThrow();
    }
}

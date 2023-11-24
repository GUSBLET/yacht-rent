package com.yachtrent.main.home;

import com.yachtrent.main.yacht.dto.YachtDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    public Slice<YachtDto> getPageNoteDto(List<YachtDto> yachts, Pageable pageable) {

        List<YachtDto> pageList = yachts.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(pageList, pageable, yachts.size());
    }
}
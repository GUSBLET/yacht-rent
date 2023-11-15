package com.yachtrent.main.home;

import com.yachtrent.main.yacht.YachtService;
import com.yachtrent.main.yacht.dto.YachtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final YachtService yachtService;

    public Slice<YachtDto> getPageNoteDto(List<YachtDto> yachts, Pageable pageable) {

        List<YachtDto> pageList = yachts.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(pageList, pageable, yachts.size());
    }

//    public PageRequest getPageRequest(int page, int size, String sort) {
//        return sort == null? PageRequest.of(page, size) : PageRequest.of(page, size, Sort.by(sort));
//    }
}
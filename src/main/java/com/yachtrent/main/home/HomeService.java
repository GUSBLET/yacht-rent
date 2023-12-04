package com.yachtrent.main.home;

import com.yachtrent.main.home.dto.FilterDto;
import com.yachtrent.main.yacht.YachtService;
import com.yachtrent.main.yacht.dto.YachtDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public List<YachtDto> createNewYachts(List<YachtDto> yachts, HttpSession session) {
        Boolean  isNewYachts = (Boolean) session.getAttribute("isNewYachts");
        if (isNewYachts != null && isNewYachts || yachts == null) {
            if(isNewYachts != null) {
                session.removeAttribute("isNewYachts");
            }
            return yachtService.findAllYachtDto();
        }
        return yachts;
    }

    public FilterDto createNewFilter(FilterDto filter, List<YachtDto> yachts, HttpSession session) {
        Boolean  isNewFilter = (Boolean) session.getAttribute("isNewFilter");
        if (isNewFilter != null && isNewFilter || filter == null) {
            FilterDto newFilter = FilterDto.builder()
                    .min(yachts.stream().min(Comparator.comparing(YachtDto::getPricePerHour))
                            .map(price -> Math.round(price.getPricePerHour()))
                            .orElse(700)
                    )
                    .max(yachts.stream().max(Comparator.comparing(YachtDto::getPricePerHour))
                            .map(price -> Math.round(price.getPricePerHour()))
                            .orElse(5000)
                    )
                    .build();

            newFilter.setMainMax(newFilter.getMax());
            newFilter.setMainMin(newFilter.getMin());

            if(isNewFilter != null) {
                session.removeAttribute("isNewFilter");
            }
            return newFilter;
        }
        return filter;
    }
}
package com.yachtrent.main.home;

import com.yachtrent.main.home.dto.FilterDto;
import com.yachtrent.main.yacht.YachtService;
import com.yachtrent.main.yacht.dto.YachtDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
    private final YachtService yachtService;
    private FilterDto filter;
    private List<YachtDto> yachts;

    @GetMapping
    public String getListNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            Model model
    ) {
        if (yachts == null ) {
            yachts =  yachtService.findAllYachtDto();
        }
        if (filter == null) {
            filter  = FilterDto.builder().min(700).max(5000).build();
        }

        model.addAttribute("filter", filter)
                .addAttribute("yachts",
                        homeService.getPageNoteDto(yachts, PageRequest.of(page, size)));
        return "account/home/home-page";
    }

    @PostMapping("/search")
    public String findYachtsByName(@RequestParam("searchName") String searchName) {
        if (searchName.isBlank()) {
            log.warn("Did not specify a name to search for yachts");
            yachts = null;
            return "redirect:/home";
        }

        yachts = yachtService.findYachtsByNameDto(searchName);
        return "redirect:/home";
    }

    @PostMapping("/filter")
    public String getFilterData(@ModelAttribute("filter") FilterDto filterDto) {
        filter = filterDto;
        yachts = yachtService.findAllYachtBySpecification(filter);
        return "redirect:/home";
    }
}

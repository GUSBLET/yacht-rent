package com.yachtrent.main.yacht;

import com.yachtrent.main.account.AccountService;
import com.yachtrent.main.yacht.dto.YachtDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/yacht")
@RequiredArgsConstructor
public class YachtController {
    private final YachtService yachtService;
    private final AccountService accountService;

    @GetMapping("/add-yacht")
    public String addYacht(@RequestParam long accountId, Model model) {
        YachtDTO yachtDTO = new YachtDTO();
        yachtDTO.setAccount(accountService.findAccountById(accountId));
        model.addAttribute("title", "New yacht")
                .addAttribute("yacht", yachtDTO);

        return "yacht/add-yacht";
    }

    @PostMapping("/add-yacht")
    public String addYacht(@Valid @ModelAttribute("yacht") YachtDTO yachtDTO,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("YachtDTO not valid");
            return "yacht/add-yacht";
        }
        if (yachtService.isYachtExists(yachtDTO.getName())) {
            model.addAttribute("error", true);
            log.warn("A yacht named \"{}\" already exists", yachtDTO.getName());
            return "yacht/add-yacht";
        }

        yachtService.addYacht(yachtDTO);
        model.addAttribute("title", "Success");
        return "redirect:/cabinet";
    }

    @PostMapping("/delete-yacht")
    public String deleteYacht(@RequestParam long id) {
        yachtService.deleteYachtById(id);
        return "redirect:/cabinet";
    }

    @GetMapping("/info")
    public String showModal(@RequestParam long id, Model model) {
        Yacht yacht = yachtService.findYachtById(id);
        model.addAttribute("yacht", yacht);
        return "yacht/show_yacht";
    }

    @GetMapping("/update")
    public String updateYacht(@RequestParam long id, Model model) {
        Yacht yacht = yachtService.findYachtById(id);
        YachtDTO yachtDTO = new YachtDTO().toDto(yacht);
        model.addAttribute("yacht", yachtDTO);
        return "yacht/update_yacht";
    }

    @PostMapping("/update")
    public String updateYacht(@Valid @ModelAttribute("yacht") YachtDTO yachtDto, Model model,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("YachtDTO not valid");
            return "yacht/update_yacht";
        }
        if (yachtService.isYachtExists(yachtDto.getName())) {
            model.addAttribute("error", true);
            log.warn("A yacht named \"{}\" already exists", yachtDto.getName());
            return "yacht/add-yacht";
        }

        Yacht yacht = yachtDto.toEntity(yachtDto);
        yachtService.updateYacht(yacht);
        return "redirect:/cabinet";
    }
}

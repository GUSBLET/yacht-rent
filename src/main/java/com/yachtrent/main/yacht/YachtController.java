package com.yachtrent.main.yacht;

import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.dto.CreatingYachtDTO;
import com.yachtrent.main.yacht.dto.RemoveYachtDTO;
import com.yachtrent.main.yacht.services.YachtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.OptionPaneUI;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/yacht")
@AllArgsConstructor
public class YachtController {

    private final YachtService yachtService;

    @PostMapping(value = "/add-yacht")
    public String addYacht(@Valid @ModelAttribute("creatingYachtDTO")
                               CreatingYachtDTO creatingYachtDTO,
                           BindingResult bindingResult,
                           Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("title", "New yacht");
            model.addAttribute("creatingYachtDTO", creatingYachtDTO);
            return "yacht/adding-yacht-modal-form";
        }
        creatingYachtDTO.setCreator(new Creator());
        ResponseEntity<String> response = yachtService.addYacht(creatingYachtDTO);
        if(response.getStatusCode() == HttpStatusCode.valueOf(400)) {
            model.addAttribute("title", "New yacht");
            model.addAttribute("creatingYachtDTO", creatingYachtDTO);
            bindingResult.addError(new FieldError("creatingYachtDTO", "name", Objects.requireNonNull(response.getBody())));
            return "yacht/adding-yacht-modal-form";
        }

        model.addAttribute("title", "Success");
        model.addAttribute("message", response.getBody());
        return "fragments/modal-success";
    }

    @PostMapping("/remove-yacht")
    public String removeYacht(@RequestParam long id, Model model){
        yachtService.removeYacht(id);
        model.addAttribute("title", "Profile");
        model.addAttribute("content", "profile");
        model.addAttribute("profileContent", "yacht");

        return "layout";
    }

    @GetMapping("/adding-yacht-modal-form")
    public String addingYachtForm(Model model){
        model.addAttribute("creatingYachtDTO", CreatingYachtDTO.builder().accountId(1l).build());

        return "/yacht/adding-yacht-modal-form";
    }

    @GetMapping("/remove-yacht-modal-form")
    public String removeYachtModal(@RequestParam long id ,Model model){
        Optional<Yacht> yacht = yachtService.findYachtById(id);
         if(yacht.isPresent()){
            model.addAttribute("yacht", yacht.get());
            model.addAttribute("path", "/yacht/remove-yacht");
            model.addAttribute("message", "Do you want to remove yacht?");
            return "/fragments/modal-confirming-byId";
        }
        model.addAttribute("title", "Error");
        model.addAttribute("message", "Yacht does not exist");
        return "fragments/modal-error";
    }
}

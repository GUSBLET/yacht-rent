package com.yachtrent.main.yacht;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.yacht.dto.CreatingYachtDTO;
import com.yachtrent.main.yacht.services.YachtService;
import com.yachtrent.main.yacht.type.Types;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/yacht")
@AllArgsConstructor
public class YahctController {

    private final YachtService yachtService;

    @PostMapping("add-yacht")
    public String addYacht(@Valid @ModelAttribute("creatingYachtDTO") CreatingYachtDTO creatingYachtDTO,
            Model model,
            BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            model.addAttribute("title", "New yacht");
//            model.addAttribute("content", "yacht/adding-yacht-page");
//            model.addAttribute("creatingYachtDTO", creatingYachtDTO);
//            return "layout";
//        }

        CreatingYachtDTO yourObject = new CreatingYachtDTO("YachtNames",
                (short) 5,
                (short) 3,
                (short) 10,
                150.0f,
                12.5f,
                3.0f,
                "A beautiful yacht for rent",
                1L,
                Types.SHIP,
                null,
                creatingYachtDTO.getPhotos());

            yachtService.addYacht(yourObject);

        return "layout";
    }

    @GetMapping("adding-yacht-page")
    public String addingYachtPage(Model model){
        model.addAttribute("title", "New yacht");
        model.addAttribute("content", "yacht/adding-yacht-page");
        model.addAttribute("creatingYachtDTO", CreatingYachtDTO.builder().accountId(1l).build());


        return "layout";
    }
}

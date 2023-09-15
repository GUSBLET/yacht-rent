package com.yachtrent.main.account;


import com.yachtrent.main.account.dto.SignUpViewModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    private IAccountService accountService;

    public AccountController(IAccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/login-page")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "account/login-page")
                .addAttribute("signUpViewModel",  new SignUpViewModel());
        return "account/login-page";
    }

    @GetMapping("/admin")
    public String admin(SignUpViewModel signUpViewModel, Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("content", "account/login-page");
        model.addAttribute("signInViewModel", signUpViewModel);
        return "layout";
    }


    @GetMapping("registration-page")
    public  String registrationPage(Model model){
        model.addAttribute("title", "Registration");
        model.addAttribute("content", "account/registration-page");
        model.addAttribute("signUpViewModel", new SignUpViewModel());
        return "layout";
    }

    @PostMapping("sign-up")
    public String signUp(@Valid @ModelAttribute("signUpViewModel") SignUpViewModel signUpViewModel,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "account/login-page";
        }
        if (accountService.signUpAsync(signUpViewModel) == null) {
            log.error("Failed authorization");
            return "account/login-page";
        }
        return "redirect:/account/success";
    }
}

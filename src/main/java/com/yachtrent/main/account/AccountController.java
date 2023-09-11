package com.yachtrent.main.account;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    record SignUp (
            @NotBlank(message = "Enter your email")
            @Email(message = "Incorrect mail entry")
            String email,

            @NotBlank(message = "Enter your password")
            String password,

            @NotBlank(message = "Confirm password")
            String passwordConfirm
    ) { }

    @GetMapping("/login-page")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "account/login-page")
                .addAttribute("signUpViewModel",  new SignUp(null, null, null));
        return "account/login-page";
    }

    @GetMapping("/success")
    public String getSuccessPage(@AuthenticationPrincipal Account account,
                                 @RequestParam(name = "rememberMe", required = false) boolean rememberMe, Model model) {
        model.addAttribute("title", "Success")
                .addAttribute("text", account);

        account.setAccountRegistered(rememberMe);
        return "account/success";
    }

    @GetMapping("/admin")
    public String admin(SignUp signUp, Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("content", "account/login-page");
        model.addAttribute("signInViewModel", signUp);
        return "layout";
    }

}

//    @PostMapping("/sign-up")
//    public String signUp(@Valid @ModelAttribute("signInViewModel") SignIn signIn,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            log.error(bindingResult.getAllErrors().toString());
//            return "account/login-page";
//        }
//        if (accountService.loadUserByUsername(signIn.email) == null) {
//            log.error("Failed authorization");
//            return "account/login-page";
//        }
//        return "redirect:/account/success";
//    }
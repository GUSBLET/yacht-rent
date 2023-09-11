package com.yachtrent.main.account;


import com.yachtrent.main.dto.account.SignUpViewModel;
import com.yachtrent.main.mail.service.MailService;
import com.yachtrent.main.mail.service.dto.MailMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        if (accountService.singUpAsync(signUpViewModel) == null) {
            log.error("Failed authorization");
            return "account/login-page";
        }
        return "redirect:/account/success";
    }

}


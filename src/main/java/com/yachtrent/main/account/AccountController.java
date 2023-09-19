package com.yachtrent.main.account;


import com.yachtrent.main.account.dto.SignUpViewModel;
import com.yachtrent.main.mail.service.MailService;
import com.yachtrent.main.mail.service.dto.MailMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final MailService mailService;

    @GetMapping("/login-page")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "account/login-page")
                .addAttribute("signUpViewModel", new SignUpViewModel());
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
    public String registrationPage(Model model) {
        model.addAttribute("title", "Registration")
                .addAttribute("content", "account/registration-page")
                .addAttribute("signUpViewModel", new SignUpViewModel());
        return "layout";
    }

    @PostMapping("sign-up")
    public String signUp(@Valid @ModelAttribute("signUpViewModel") SignUpViewModel signUpViewModel,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "account/registration-page";
        }
        if (accountService.userExists(signUpViewModel.getEmail())) {
            model.addAttribute("error", true);
            log.error("Failed sign up");
            return "account/registration-page";
        }

        Account account = accountService.signUpNewUser(signUpViewModel);
        model.addAttribute("success", true);
        String url = "http://localhost:8080/account/login-page/" + account.getId();
        mailService.sendMail(new MailMessage(signUpViewModel.getEmail(),
                url,
                "confirm your email"));
        return "account/registration-page";
    }

    @GetMapping("/login-page/{key}")
    public String confirmMail(@PathVariable("key") Long key) {
        Account account = accountRepository.findById(key).get();
        if (account != null) {
            account.setAccountRegistered(true);
            return "account/login-page";
        }
        return "account/registration-page";
    }
}

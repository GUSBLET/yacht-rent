package com.yachtrent.main.account;


import com.yachtrent.main.account.dto.SignUpViewModel;
import com.yachtrent.main.account.token.Token;
import com.yachtrent.main.account.token.TokenRepository;
import com.yachtrent.main.account.token.TokenService;
import com.yachtrent.main.techniacal.mail.service.MailService;
import com.yachtrent.main.techniacal.mail.service.dto.MailMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final MailService mailService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

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

    @GetMapping("/registration-page")
    public String registrationPage(Model model) {
        model.addAttribute("title", "Registration")
                .addAttribute("content", "account/registration-page")
                .addAttribute("signUpViewModel", new SignUpViewModel());
        return "layout";
    }

    @PostMapping("/sign-up")
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

        Token token = tokenService.generateAndSaveToken(account);
        String url = "http://localhost:8080/account/verify/" + token.getToken();

        mailService.sendMail(new MailMessage(signUpViewModel.getEmail(),
                url,
                "confirm your email")
        );

        return "account/registration-page";
    }

    @GetMapping("/verify/{token}")
    public String confirmMail(@PathVariable("token") String token, Model model) {
        Token confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException(token + " not exist"));

        if (!tokenService.verifyToken(confirmationToken)) {
            model.addAttribute("verify", true);
            return "account/registration-page";
        }

        confirmationToken.setConfirmationAt(LocalDateTime.now());
        Account account = accountRepository.findById(confirmationToken.getAccount().getId()).orElseThrow();
        account.setAccountConfirmed(true);
        accountRepository.updateAccount(account.getId(), account);
        return "account/login-page";
    }
}

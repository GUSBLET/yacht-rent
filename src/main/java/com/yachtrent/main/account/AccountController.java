package com.yachtrent.main.account;


import com.yachtrent.main.account.dto.SignUp;
import com.yachtrent.main.account.token.Token;
import com.yachtrent.main.account.token.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TokenService tokenService;

    @GetMapping("/login-page")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "account/login-page")
                .addAttribute("signUp", new SignUp());
        return "account/login-page";
    }

    @GetMapping("/admin")
    public String admin(SignUp signUp, Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "account/login-page")
                .addAttribute("signUp", signUp);
        return "layout";
    }

    @GetMapping("/registration-page")
    public String registrationPage(Model model) {
        model.addAttribute("title", "Registration")
                .addAttribute("content", "account/registration-page")
                .addAttribute("signUp", new SignUp());
        return "account/registration-page";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("signUp") SignUp signUp,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "account/registration-page";
        }
        if (accountService.userExists(signUp.getEmail())) {
            model.addAttribute("error", true);
            log.error("Failed sign up");
            return "account/registration-page";
        }

        accountService.signUpNewAccountAndSendEmail(signUp);
        model.addAttribute("success", true)
                .addAttribute("mail", signUp.getEmail());
        return "account/confirm-register";
    }

    @GetMapping("/verify/{token}")
    public String confirmMail(@PathVariable("token") String token, Model model) {
        Token confirmationToken = tokenService.findToken(token);

        if (!tokenService.verifyToken(confirmationToken)) {
            accountService.deleteAccount(confirmationToken.getAccount().getEmail());
            model.addAttribute("verify", true)
                    .addAttribute("signUp", new SignUp());
            return "account/registration-page";
        }

        accountService.confirmAccount(confirmationToken);
        model.addAttribute("success", true);
        return "account/login-page";
    }
}

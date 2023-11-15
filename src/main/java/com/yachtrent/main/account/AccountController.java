package com.yachtrent.main.account;


import com.yachtrent.main.account.dto.EditAccount;
import com.yachtrent.main.account.dto.EnterEmail;
import com.yachtrent.main.account.dto.Password;
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

    @GetMapping("/edit-account")
    public String getEditPage(@RequestParam long id,
                              @RequestParam(name = "changeEmail", required = false, defaultValue = "false") boolean changeEmail,
                              Model model) {
        Account account = accountService.findAccountById(id);
        EditAccount editAccount = new EditAccount().toDto(account);
        editAccount.setChangeEmail(changeEmail);
        model.addAttribute("profile", editAccount)
                .addAttribute("changeEmail", changeEmail);
        return "account/edit-account";
    }

    @PostMapping("/edit-account")
    public String editAccount(@Valid @ModelAttribute("profile") EditAccount editAccount,
                              BindingResult bindingResult,
                              Model model
    ) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "account/edit-account";
        }
        if (!accountService.isBelongsEmailProvideId(editAccount.getId(), editAccount.getEmail())) {
            model.addAttribute("isEmailExists", true)
                    .addAttribute("changeEmail", true);
            log.warn("A email \"{}\" already exists", editAccount.getEmail());
            return "account/edit-account";
        }

        Account account = editAccount.toEntity(editAccount);
        accountService.updateAccount(account);
        return "redirect:/cabinet";
    }

    @GetMapping("/change-password")
    public String getChangePasswordPage(Model model) {
        model.addAttribute("enterEmail", new EnterEmail());
        return "account/enter-email";
    }

    @PostMapping("/change-password")
    public String checkPassword(@Valid @ModelAttribute("enterEmail") EnterEmail email,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "account/enter-email";
        }
        if (!accountService.userExists(email.getEmail())) {
            model.addAttribute("existsEmail", true);
            log.error("Failed sign up");
            return "account/enter-email";
        }

        accountService.sendEmail(email.getEmail(), "change-password");
        model.addAttribute("success", true);
        return "account/enter-email";
    }

    @GetMapping("/verify/{event}/{token}")
    public String confirmMail(@PathVariable("event") String event,
                              @PathVariable("token") String token,
                              Model model) {
        Token confirmationToken = tokenService.findToken(token);

        if (!tokenService.verifyToken(confirmationToken)) {
            String url = "account/" + event;
            model.addAttribute("verify", true);
            return url;
        }

        //TODO скорее всего надо вынести в отдельный метод
        return "change-password".equals(event) ? "redirect:/account/password/" + confirmationToken.getAccount().getId()
                : "account/login-page";
    }

    @GetMapping("/password/{id}")
    public String changePassword(@PathVariable("id") long id, Model model) {
        Password password = new Password();
        password.setId(id);
        model.addAttribute("password", password);
        return "account/new-password";
    }

    @PostMapping("/password")
    public String sendEmail(@Valid @ModelAttribute("password") Password password,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "account/new-password";
        }

        accountService.updatePassword(password.getPassword(), password.getId());
        model.addAttribute("success_change_password", true);
        return "account/login-page";
    }

    @GetMapping("/change-email/{id}")
    public String getChangeEmailPage(@PathVariable("id") long id, Model model) {
        Password password = new Password();
        password.setId(id);
        model.addAttribute("password", password);
        return "account/enter-password";
    }

    @PostMapping("/change-email")
    public String checkPasswordForChangeEmail(@Valid @ModelAttribute("password") Password password,
                                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            return "account/enter-password";
        }
        if (!accountService.isPasswordCorrect(password.getPassword(), password.getId())) {
            model.addAttribute("incorrectPassword", true);
            log.error("Incorrect password");
            return "account/enter-password";
        }

        return "redirect:/account/edit-account?id=" + password.getId() + "&changeEmail=" + true;
    }
}

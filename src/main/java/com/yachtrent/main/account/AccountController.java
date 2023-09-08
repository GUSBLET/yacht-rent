package com.yachtrent.main.account;


import com.yachtrent.main.dto.account.SignInViewModel;
import com.yachtrent.main.dto.account.SignUpViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Controller
@RequestMapping("/account/")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

//    public AccountController(IAccountService accountService) {
//        this.accountService = accountService;
//    }
//    @PostMapping("sing-in")
//    public String singIn(@Valid @ModelAttribute("signInViewModel")
//                             SignInViewModel signInViewModel,
//                         BindingResult result,
//                         Model model,
//                         HttpServletRequest request,
//                         HttpServletResponse response){
//        if(result.hasErrors()){
//            model.addAttribute("title", "Login");
//            model.addAttribute("content", "account/login-page");
//            model.addAttribute("signInViewModel", signInViewModel);
//            model.addAttribute("signUpViewModel", new SignUpViewModel());
//            return "layout";
//        }
//
//
//        model.addAttribute("title", "Success");
//        model.addAttribute("text", "Success");
//        return "account/success";
//    }

//    @Async
//    @PostMapping("sign-up")
//    public CompletableFuture<String> signUp(@Valid @RequestBody SignUpViewModel signUpViewModel,
//                                            BindingResult result,
//                                            Model model
//    ) {
//        if (result.hasErrors()) {
//            log.error(result.getAllErrors().toString());
//            return CompletableFuture.completedFuture("account/login-page");
//        }
//
//        model.addAttribute("title", "Login")
//                .addAttribute("content", "account/login-page")
//                .addAttribute("signInViewModel", new SignInViewModel())
//                .addAttribute("signUpViewModel", signUpViewModel);
//
//        CompletableFuture<BindingResult> bindingResultCompletableFuture =
//                accountService.singUpAsync(signUpViewModel);
//
//        model.addAttribute("title", "Success")
//                .addAttribute("text", "Success, confirm mail");
//        return CompletableFuture.completedFuture("account/success");
//    }

    @GetMapping("login-page")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "account/login-page")
                .addAttribute("signInViewModel", new SignInViewModel())
                .addAttribute("signUpViewModel", new SignUpViewModel());
        return "layout";
    }

    @GetMapping("admin")
    public String admin(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("content", "account/login-page");
        model.addAttribute("signInViewModel", new SignInViewModel());
        model.addAttribute("signUpViewModel", new SignUpViewModel());
        return "layout";
    }


    @GetMapping("success")
    public String getSuccessPage(@AuthenticationPrincipal Account account, Model model) {
        model.addAttribute("title", "Success")
                .addAttribute("text", account);
        return "account/success";
    }

}

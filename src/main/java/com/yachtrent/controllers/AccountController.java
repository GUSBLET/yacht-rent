package com.yachtrent.controllers;


import com.yachtrent.domain.view.models.account.SignInViewModel;
import com.yachtrent.domain.view.models.account.SignUpViewModel;
import com.yachtrent.interfaces.IAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/account/")
public class AccountController {
    @Autowired
    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

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

    @Async
    @PostMapping("sing-up")
    public CompletableFuture<String> singUp(@Valid @ModelAttribute("signUpViewModel")
                                                SignUpViewModel signUpViewModel,
                                            BindingResult result,
                                            Model model){
        if(result.hasErrors()){
            model.addAttribute("title", "Login");
            model.addAttribute("content", "account/login-page");
            model.addAttribute("signInViewModel", new SignInViewModel());
            model.addAttribute("signUpViewModel", signUpViewModel);
            return CompletableFuture.completedFuture("layout");
        }
        accountService.singUpAsync(signUpViewModel);

        model.addAttribute("title", "Success");
        model.addAttribute("text", "Success, confirm mail");
        return CompletableFuture.completedFuture("account/success");
    }

    @GetMapping("login-page")
    public String loginPage(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("content", "account/login-page");
        model.addAttribute("signInViewModel", new SignInViewModel());
        model.addAttribute("signUpViewModel", new SignUpViewModel());
        return "layout";
    }

    @GetMapping("admin")
    public String admin(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("content", "account/login-page");
        model.addAttribute("signInViewModel", new SignInViewModel());
        model.addAttribute("signUpViewModel", new SignUpViewModel());
        return "layout";
    }

}

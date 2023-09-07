package com.yachtrent.main.account;

import com.yachtrent.main.dto.account.SignInViewModel;
import com.yachtrent.main.dto.account.SignUpViewModel;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    public CompletableFuture<BindingResult> singUpAsync(SignUpViewModel model);
    public CompletableFuture<BindingResult> singInAsync(SignInViewModel model);
}

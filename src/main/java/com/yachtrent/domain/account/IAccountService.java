package com.yachtrent.domain.account;

import com.yachtrent.domain.dto.SignInViewModel;
import com.yachtrent.domain.dto.SignUpViewModel;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    public CompletableFuture<BindingResult> singUpAsync(SignUpViewModel model);
    public CompletableFuture<BindingResult> singInAsync(SignInViewModel model);
}

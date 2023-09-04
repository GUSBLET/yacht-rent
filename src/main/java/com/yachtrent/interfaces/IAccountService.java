package com.yachtrent.interfaces;

import com.yachtrent.domain.view.models.account.SignInViewModel;
import com.yachtrent.domain.view.models.account.SignUpViewModel;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    public CompletableFuture<BindingResult> singUpAsync(SignUpViewModel model);
    public CompletableFuture<BindingResult> singInAsync(SignInViewModel model);

}

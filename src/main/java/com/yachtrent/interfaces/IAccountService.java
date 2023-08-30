package com.yachtrent.interfaces;

import com.yachtrent.domain.view.models.SingInViewModel;
import com.yachtrent.domain.view.models.SingUpViewModel;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    public CompletableFuture<BindingResult> singUpAsync(SingUpViewModel model);
    public CompletableFuture<BindingResult> singInAsync(SingInViewModel model);

}

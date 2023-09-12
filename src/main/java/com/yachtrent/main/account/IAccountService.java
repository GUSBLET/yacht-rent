package com.yachtrent.main.account;

import com.yachtrent.main.dto.account.SignUpViewModel;
import com.yachtrent.main.dto.order.CreateOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    public CompletableFuture<BindingResult> signUpAsync(SignUpViewModel model);

    public ResponseEntity<Account> signUpAnonymous(CreateOrderDTO model);

}

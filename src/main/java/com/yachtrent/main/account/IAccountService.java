package com.yachtrent.main.account;

import com.yachtrent.main.account.dto.SignUpViewModel;
import com.yachtrent.main.order.dto.CreateOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    public CompletableFuture<BindingResult> signUp(SignUpViewModel model);

    public ResponseEntity<Account> signUpAnonymous(CreateOrderDTO model);

}

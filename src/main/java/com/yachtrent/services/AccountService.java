package com.yachtrent.services;

import com.google.common.hash.Hashing;
import com.yachtrent.domain.enums.Role;
import com.yachtrent.use_cases.CheckingAccountDetailsUseCase;
import com.yachtrent.databaselayer.repositories.AccountRepository;
import com.yachtrent.domain.entities.Account;
import com.yachtrent.domain.view.models.SingInViewModel;
import com.yachtrent.domain.view.models.SingUpViewModel;
import com.yachtrent.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final CheckingAccountDetailsUseCase checkingAccountDetailsUseCase;

    public AccountService(AccountRepository accountRepository, CheckingAccountDetailsUseCase checkingAccountDetailsUseCase) {
        this.accountRepository = accountRepository;
        this.checkingAccountDetailsUseCase = checkingAccountDetailsUseCase;
    }

    @Override
    @Async
    public CompletableFuture<BindingResult> singUpAsync(SingUpViewModel model) {
        BindingResult result = new BeanPropertyBindingResult(model, "singUpViewModel");
        Optional<Account> response = accountRepository.findByEmailOrLogin(model.getLogin(),
                model.getEmail());
        if(response.isEmpty()){
            if(checkingAccountDetailsUseCase.emailMask(model.getPassword())
                    && checkingAccountDetailsUseCase.passwordCompare(model.getPassword(), model.getPasswordConfirm())){
                Account account = new Account();
                account.setEmail(model.getEmail());
                account.setLogin(model.getLogin());
                account.setPassword(Hashing.sha256().hashString(model.getPassword(), StandardCharsets.UTF_8).toString());
                account.setRole(Role.admin);
                accountRepository.save(account);
                result.reject("200", "success");
            }
            else{
                result.reject("500", "password have to be more than 8 chars, or password dont compare");
            }

        }
        else{
            result.reject("500", "login or email have already existed");
        }
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<BindingResult> singInAsync(SingInViewModel model) {
        return null;
    }
}

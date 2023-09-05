package com.yachtrent.domain.account;

import com.google.common.hash.Hashing;
import com.yachtrent.domain.account.use_cases.CheckingAccountDetailsUseCase;
import com.yachtrent.domain.dto.SignInViewModel;
import com.yachtrent.domain.dto.SignUpViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final CheckingAccountDetailsUseCase checkingAccountDetailsUseCase;

//    public AccountService(AccountRepository accountRepository, CheckingAccountDetailsUseCase checkingAccountDetailsUseCase) {
//        this.accountRepository = accountRepository;
//        this.checkingAccountDetailsUseCase = checkingAccountDetailsUseCase;
//    }

    @Override
    @Async
    public CompletableFuture<BindingResult> singUpAsync(SignUpViewModel model) {
        BindingResult result = new BeanPropertyBindingResult(model, "singUpViewModel");
        Optional<Account> response = accountRepository.findByEmailOrLogin(model.getLogin(), model.getEmail());
        if(response.isEmpty()){
            if(checkingAccountDetailsUseCase.emailMask(model.getPassword())
                    && checkingAccountDetailsUseCase.passwordCompare(model.getPassword(), model.getPasswordConfirm())){
                Account account = Account.builder()
                        .email(model.getEmail())
                        .login(model.getLogin())
                        .password(Hashing.sha256().hashString(model.getPassword(), StandardCharsets.UTF_8).toString())
                        .build();

                //account.setRoles(Set.of(admin));
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
    public CompletableFuture<BindingResult> singInAsync(SignInViewModel model) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) accountRepository.findByLogin(username).orElseThrow(() -> new IllegalArgumentException("s"));
    }
}

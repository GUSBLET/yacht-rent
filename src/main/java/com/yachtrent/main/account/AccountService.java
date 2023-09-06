package com.yachtrent.main.account;

import com.google.common.hash.Hashing;
import com.yachtrent.main.account.use_cases.CheckingAccountDetailsUseCase;
import com.yachtrent.main.dto.SignInViewModel;
import com.yachtrent.main.dto.SignUpViewModel;
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

    @Override
    @Async
    public CompletableFuture<BindingResult> singUpAsync(SignUpViewModel model) {
        BindingResult result = new BeanPropertyBindingResult(model, "singUpViewModel");
        Optional<Account> response = accountRepository.findByEmail(model.getEmail());
        if(response.isEmpty()){
            if(checkingAccountDetailsUseCase.emailMask(model.getPassword())
                    && checkingAccountDetailsUseCase.passwordCompare(model.getPassword(), model.getPasswordConfirm())){
                Account account = Account.builder()
                        .email(model.getEmail())
                        .password(Hashing.sha256().hashString(model.getPassword(), StandardCharsets.UTF_8).toString())
                        .build();

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
        return (UserDetails) accountRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("s"));
    }
}

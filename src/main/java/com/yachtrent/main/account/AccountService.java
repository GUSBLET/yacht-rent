package com.yachtrent.main.account;

import com.google.common.hash.Hashing;
import com.yachtrent.main.account.use_cases.CheckingAccountDetailsUseCase;
import com.yachtrent.main.dto.account.SignInViewModel;
import com.yachtrent.main.dto.account.SignUpViewModel;
import com.yachtrent.main.role.Authority;
import com.yachtrent.main.role.Role;
import com.yachtrent.main.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService, UserDetailsService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final CheckingAccountDetailsUseCase checkingAccountDetailsUseCase;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Async
    public CompletableFuture<BindingResult> singUpAsync(SignUpViewModel model) {
        BindingResult result = new BeanPropertyBindingResult(model, "singUpViewModel");
        Optional<Account> response = accountRepository.findByEmail(model.getEmail());
        if (response.isEmpty()) {
            if (checkingAccountDetailsUseCase.emailMask(model.getPassword())
                    && checkingAccountDetailsUseCase.passwordCompare(model.getPassword(), model.getPasswordConfirm())) {

                Role role = roleRepository.save(
                        Role.builder()
                                .role(Authority.USER.toString())
                                .build());

                Account account = Account.builder()
                        .email(model.getEmail())
                        .password(Hashing.sha256().hashString(model.getPassword(), StandardCharsets.UTF_8).toString())
                        .roles(new HashSet<>())
                        .name("")
                        .lastName("")
                        .phoneNumber("")
                        .accountConfirmed(false)
                        .accountRegistered(true)
                        .build();

                account.getRoles().add(role);
                accountRepository.save(account);
                result.reject("200", "success");
            } else {
                result.reject("500", "password have to be more than 8 chars, or password dont compare");
            }

        } else {
            result.reject("500", "login or email have already existed");
        }
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<BindingResult> singInAsync(SignInViewModel model) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such email exists"));
    }

}

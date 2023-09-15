package com.yachtrent.main.account;

import com.google.common.hash.Hashing;
import com.yachtrent.main.account.use_cases.CheckingAccountDetailsUseCase;
import com.yachtrent.main.account.dto.SignUpViewModel;
import com.yachtrent.main.order.dto.CreateOrderDTO;
import com.yachtrent.main.role.Authority;
import com.yachtrent.main.role.Role;
import com.yachtrent.main.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public Account getAccount(long id){
        return accountRepository.findById(id).orElseThrow(() ->{
            return null;
        });
    }

    @Override
    @Async
    public CompletableFuture<BindingResult> signUpAsync(SignUpViewModel model) {
        BindingResult result = new BeanPropertyBindingResult(model, "singUpViewModel");
        Optional<Account> response = accountRepository.findByEmail(model.getEmail());
        if (response.isEmpty()) {
            if (checkingAccountDetailsUseCase.emailMask(model.getPassword())
                    && checkingAccountDetailsUseCase.passwordCompare(model.getPassword(), model.getPasswordConfirm())) {

                Optional<Role> role = Optional.of(roleRepository.findByRole(model.getRole().toString()).orElseGet(() -> {
                    return roleRepository.findByRole(Authority.ANONYMOUS.toString()).get();
                }));

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

                account.getRoles().add(role.get());
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

    // Method create new account if user does not exist and anonymous creates new order.
    @Override
    public ResponseEntity<Account> signUpAnonymous(CreateOrderDTO model) {
        Optional<Account> account = accountRepository.findByEmail(model.getCustomerEmail());
        if(account.isEmpty()){
            Optional<Role> role = roleRepository.findByRole(Authority.ANONYMOUS.toString());

            Account newAccount = Account.builder()
                    .email(model.getCustomerEmail())
                    .password("")
                    .roles(new HashSet<>())
                    .name(model.getCustomerName())
                    .lastName(model.getCustomerLastName())
                    .phoneNumber(model.getCustomerPhoneNumber())
                    .accountConfirmed(false)
                    .accountRegistered(false)
                    .build();

            newAccount.getRoles().add(role.get());
            accountRepository.save(newAccount);
            return ResponseEntity.status(HttpStatus.OK).body(newAccount);
        }
        account.get().setLastName(model.getCustomerLastName());
        account.get().setName(model.getCustomerName());
        account.get().setPhoneNumber(model.getCustomerPhoneNumber());
        accountRepository.updateAccount(account.get().getId(), account.get());
        return ResponseEntity.status(HttpStatus.OK).body(account.get());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such email exists"));
    }

}

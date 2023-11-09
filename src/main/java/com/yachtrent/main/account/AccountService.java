package com.yachtrent.main.account;

import com.yachtrent.main.account.dto.SignUp;
import com.yachtrent.main.account.token.Token;
import com.yachtrent.main.account.token.TokenService;
import com.yachtrent.main.order.dto.CreateOrderDTO;
import com.yachtrent.main.role.RoleService;
import com.yachtrent.main.techniacal.mail.service.MailService;
import com.yachtrent.main.techniacal.mail.service.dto.MailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RoleService roleService;
    private final MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such email exists"));
    }

    public Account findAccountById(long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public Account signUpAnonymous(CreateOrderDTO orderDTO) {
        Account anonymous = createAnonymous(orderDTO);
        if (!userExists(anonymous.getEmail())) {
            return accountRepository.save(anonymous);
        }
        accountRepository.updateAccount(anonymous.getId(), anonymous);
        return anonymous;
    }

    private Account createAnonymous(CreateOrderDTO createOrder) {
        return Account.builder()
                .email(createOrder.getCustomerEmail())
                .roles(roleService.getAnonymousRights())
                .name(createOrder.getCustomerName())
                .lastName(createOrder.getCustomerLastName())
                .phoneNumber(createOrder.getCustomerPhoneNumber())
                .accountConfirmed(false)
                .accountRegistered(false)
                .build();
    }

    public void signUpNewAccountAndSendEmail(SignUp signUp) {
        Account account = signUpNewAccount(signUp);
        Token token = tokenService.generateAndSaveToken(account);
        String url = "http://localhost:8080/account/verify/" + token.getToken();
        mailService.sendMail(new MailMessage(signUp.getEmail(), url, "confirm your email"));
    }

    private Account signUpNewAccount(SignUp newUser) {
        return accountRepository.save(Account.builder()
                .name(newUser.getName())
                .lastName(newUser.getLastName())
                .phoneNumber(newUser.getPhoneNumber())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .roles(roleService.getUserRights())
                .accountConfirmed(false)
                .accountRegistered(true)
                .build()
        );
    }

    public void confirmAccount(Token token) {
        token.setConfirmationAt(LocalDateTime.now());
        Account account = accountRepository.findById(token.getAccount().getId()).orElseThrow();
        account.setAccountConfirmed(true);
        accountRepository.updateAccount(account.getId(), account);
    }

    public boolean userExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    //TODO надо сделать два метода в будущем
    public void deleteAccount(String email) {
        try {
            if (userExists(email)) {
                accountRepository.deleteByEmail(email);
                log.debug("user under email: {} successfully deleted from the database", email);
            }
        } catch (UsernameNotFoundException e) {
            log.error("User not found");
        } catch (IllegalArgumentException e) {
            log.error("Email not found");
        }
    }

    private void changePassword(String oldPassword, String newPassword) {

    }
}

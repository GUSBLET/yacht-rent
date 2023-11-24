package com.yachtrent.main.account;

import com.yachtrent.main.account.dto.Profile;
import com.yachtrent.main.account.dto.SignUp;
import com.yachtrent.main.account.token.Token;
import com.yachtrent.main.account.token.TokenService;
import com.yachtrent.main.role.RoleService;
import com.yachtrent.main.techniacal.mail.service.MailService;
import com.yachtrent.main.techniacal.mail.service.dto.MailMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        return accountRepository.findByEmail(email).filter(account -> !account.isBlocked())
                .orElseThrow(() -> new UsernameNotFoundException("No such email exists"));
    }

    public Account findAccountById(long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public void signUpNewAccountAndSendEmail(SignUp signUp) {
        Account account = signUpNewAccount(signUp);
        Token token = tokenService.generateAndSaveToken(account);
        String url = "http://localhost:8080/account/verify/" + token.getToken();
        mailService.sendMail(new MailMessage(signUp.getEmail(), url, "confirm your email"));
    }

    public void sendEmail(String email, String event) {
        Account account = accountRepository.findByEmail(email).orElseThrow();
        Token token = tokenService.generateAndSaveToken(account);
        String url = "http://localhost:8080/account/verify/" + event + "/" + token.getToken();
        mailService.sendMail(new MailMessage(email, url, "confirm your email"));
    }

    private Account signUpNewAccount(SignUp newUser) {
        return accountRepository.save(Account.builder()
                .name(newUser.getName())
                .lastName(newUser.getLastName())
                .phoneNumber(newUser.getPhoneNumber())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .roles(roleService.getChooseRole(newUser.getRole()))
                .accountConfirmed(false)
                .accountRegistered(true)
                .build()
        );
    }

    public Account getAccount(Long accountId) {
        return accountId != null ? accountRepository.findById(accountId).orElseThrow() : getAuthentication();
    }

    public Account getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !auth.getPrincipal().equals("anonymousUser") ? (Account) auth.getPrincipal() : null;
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

    @Modifying
    @Transactional
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

    public boolean isPasswordCorrect(String password, long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return isPasswordMatches(password, account.getPassword());
    }

    private boolean isPasswordMatches(String password, String passwordUser) {
        return passwordEncoder.matches(password, passwordUser);
    }

    @Modifying
    @Transactional
    public void updateAccount(@NonNull Account account) {
        accountRepository.save(account);
        log.info("success update yacht");
    }

    public void updatePassword(String password, long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.updateAccount(account.getId(), account);
    }

    public boolean isBelongsEmailProvideId(Long id, String email) {
        Account account = accountRepository.findByEmail(email).orElse(null);

        if (account != null) {
            return account.getId().equals(id);
        }
        return true;
    }

    public Slice<Profile> getPageProfile(List<Profile> profiles, Pageable pageable) {
        List<Profile> pageList = profiles.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(pageList, pageable, profiles.size());
    }

    public List<Profile> findAllProfile() {
        return new Profile().toDtoList(accountRepository.findUsersByRoles());
    }

    public List<Profile> findAccountByName(String name) {
        return new Profile().toDtoList(accountRepository.findAccountsByName(name));
    }

    public boolean isAccountBlocked(long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return account.isBlocked();
    }

    public String redirectAfterConfirmMail(String event, Token token) {
        return "change-password".equals(event) ? "redirect:/account/password/" + token.getAccount().getId()
                : "account/login-page";
    }

    @Modifying
    @Transactional
    public void changeStatusAccountBlocked(long id, boolean isBlocked) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setBlocked(isBlocked);
        accountRepository.updateAccount(account.getId(), account);
    }

    @Modifying
    @Transactional
    public void updateRoleAccount(long id, String role) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setRoles(roleService.getChooseRole(role));
        accountRepository.updateAccount(account.getId(), account);
    }
}

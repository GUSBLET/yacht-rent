package com.yachtrent.main.account.token;

import com.yachtrent.main.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    //TODO переделать время токена
    public Token generateAndSaveToken(Account account) {
        return tokenRepository.save(Token.builder()
                .token(UUID.randomUUID().toString())
                .timeOfCreation(LocalDateTime.now())
                .lifetime(LocalDateTime.now().plusMinutes(5))
                .account(account)
                .build());
    }

    public boolean verifyToken(Token token) {
        if (token.getConfirmationAt() != null) {
            log.error("email confirmation", new IllegalArgumentException());
            return false;
        }
        if (token.getLifetime().isBefore(LocalDateTime.now())) {
            log.error("the life of the link is over", new IllegalArgumentException());
            return false;
        }
        return true;
    }
}

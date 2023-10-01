package com.yachtrent.main.mapper;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.techniacal.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Slf4j
class MapperTest {

    private final Mapper<TestAccountDto, Account> mapper = new TestClass();

    @Test
    void testToDto() {
        Account testAccount = createTestAccount();
        TestAccountDto testDto = mapper.toDto(testAccount);
        TestAccountDto secondTestDto = mapper.toDto(testAccount);

        log.info("Test account: " + testAccount.toString());
        log.info("Test dto: " + testDto.toString());
        log.info("Test secondTestDto: " + secondTestDto.toString());

        Assertions.assertDoesNotThrow(() -> mapper.toDto(testAccount));
        Assertions.assertEquals(secondTestDto, testDto);
    }

    @Test
    void testToEntity() {
        Account testAccount = createTestAccount();
        TestAccountDto testDto = mapper.toDto(testAccount);
        Account secondTestAccount = mapper.toEntity(testDto);

        log.info("Test account: " + testAccount.toString());
        log.info("Test dto: " + testDto.toString());
        log.info("Test secondTestAccount: " + secondTestAccount.toString());

        Assertions.assertDoesNotThrow(() -> mapper.toEntity(testDto));
        Assertions.assertEquals(secondTestAccount.getId(), testAccount.getId());
    }

    @Test
    void testToDtoList() {
        List<Account> testAccountList = new LinkedList<>();
        testAccountList.add(createTestAccount());
        testAccountList.add(createTestAccount());
        testAccountList.add(createTestAccount());

        List<TestAccountDto> testDtoList = mapper.toDtoList(testAccountList);

        log.info("Test account: " + testAccountList);
        log.info("Test dto: " + testDtoList.toString());

        Assertions.assertDoesNotThrow(() -> mapper.toDtoList(testAccountList));
        Assertions.assertEquals(testDtoList.stream().map(TestAccountDto::id).findFirst(),
                testAccountList.stream().map(Account::getId).findFirst()
        );
    }

    @Test
    void testToEntityList() {
        List<Account> testAccountList = new LinkedList<>();
        testAccountList.add(createTestAccount());
        testAccountList.add(createTestAccount());
        testAccountList.add(createTestAccount());

        List<TestAccountDto> testTestAccountDtoList = mapper.toDtoList(testAccountList);
        List<Account> testSecondAccountList = mapper.toEntityList(testTestAccountDtoList);

        log.info("Test account: " + testTestAccountDtoList);
        log.info("Test dto: " + testSecondAccountList.toString());

        Assertions.assertDoesNotThrow(() -> mapper.toDtoList(testAccountList));
        Assertions.assertDoesNotThrow(() -> mapper.toEntityList(testTestAccountDtoList));
        Assertions.assertEquals(
                testSecondAccountList.stream().map(Account::toString).findFirst(),
                testAccountList.stream().map(Account::toString).findFirst()
        );
    }

    public Account createTestAccount() {
        return Account.builder()
                .id(new Random().nextLong())
                .email("testuser@gmail.com")
                .name("Stepan")
                .lastName("Mychacho")
                .accountConfirmed(true)
                .accountRegistered(true)
                .build();
    }

    record TestAccountDto(
            Long id,
            String email,
            String name,
            String lastName,
            boolean accountConfirmed,
            boolean accountRegistered
    ) {
    }

    static class TestClass implements Mapper<TestAccountDto, Account> {

        @Override
        public TestAccountDto toDto(Account entity) {
            return new TestAccountDto(
                    entity.getId(),
                    entity.getEmail(),
                    entity.getName(),
                    entity.getLastName(),
                    entity.isAccountConfirmed(),
                    entity.isAccountRegistered());
        }

        @Override
        public Account toEntity(TestAccountDto dto) {
            return Account.builder()
                    .id(dto.id)
                    .email(dto.email)
                    .name(dto.name)
                    .lastName(dto.lastName)
                    .accountConfirmed(dto.accountConfirmed)
                    .accountRegistered(dto.accountRegistered)
                    .build();
        }
    }
}
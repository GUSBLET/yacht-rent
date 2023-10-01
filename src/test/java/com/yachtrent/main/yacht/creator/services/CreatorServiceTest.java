package com.yachtrent.main.yacht.creator.services;

import com.yachtrent.main.yacht.creator.CreatingCreatorDTO;
import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.creator.CreatorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CreatorServiceTest {

    @Autowired
    private CreatorService creatorService;

    @MockBean
    private CreatorRepository creatorRepository;

    @Test
    void getByCreatorCompanyNameWhenCompanyNameIsExist(){
        Creator creator = Creator.builder()
                .mail("Name")
                .companyName("Name")
                .phone("Name")
                .id(1L).build();
        when(creatorRepository.findByCompanyName("Name")).thenReturn(Optional.ofNullable(creator));

        Creator response = creatorService.getByCreatorCompanyName("Name");

        assertEquals(
                creator, response);
    }

    @Test
    void createCreatorWhenCreatorExist() {
        when(creatorRepository.findByCompanyNameOrMailOrPhone(
                "CompanyTest",
                "MailTest",
                "PhoneTest")).thenReturn(Optional.ofNullable(Creator.builder().id(1L).build()));

        ResponseEntity<String> response = creatorService.createCreator(CreatingCreatorDTO.builder().phone("PhoneTest").companyName("CompanyTest").mail("MailTest").build());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createCreatorWhenCreatorDoesNotExist() {
        when(creatorRepository.findByCompanyNameOrMailOrPhone(
                "CompanyTest",
                "MailTest",
                "PhoneTest")).thenReturn(Optional.empty());

        ResponseEntity<String> response = creatorService.createCreator(CreatingCreatorDTO.builder().phone("PhoneTestNew").companyName("CompanyTestNew").mail("MailTestNew").build());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
package com.yachtrent.main.account;

import com.yachtrent.main.account.dto.SignUp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
* ��������� @WebMvcTest �������� ���������� ���������� �� ���������� �������
* � ������������� �������� ������(���� �������� ����� �� ��������� �������� ������)
*
* ������ �� ���:
* https://docs.spring.io/spring-framework/reference/testing/webtestclient.html
* */
@WebMvcTest(AccountController.class)
//�������� ������ �������(������� ���������) ��� ������� (��� ������ ������ ����)
@AutoConfigureMockMvc(addFilters = false)
class TestLoginAndRegister {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUp signUp;

    @Test
    void testLoginPage() throws Exception {
        this.mockMvc.perform(get("/account/login-page"))
                .andExpectAll(
                        status().isOk(),
                        model().attribute("title", "Login"),
                        model().attribute("content", "account/login-page"),
                        model().attribute("signUp",
                                new SignUp()),
                        view().name("account/login-page"))
                .andDo(log());
    }

    @Test
    void testRegistrationPage() throws Exception {
        this.mockMvc.perform(get("/account/registration-page"))
                .andExpectAll(
                        status().isOk(),
                        model().attribute("title", "Registration"),
                        model().attribute("content", "account/registration-page"),
                        model().attribute("signUp", new SignUp()),
                        view().name("layout"))
                .andDo(log());
    }

//    @Test
//    void TestSignUp() throws Exception {
//        when(accountService.signUpAsync(signUp)).getMock();
//
//        this.mockMvc.perform(post("/account/sign-up"))
//                        .param("email", "test@gmail.com")
//                        .param("password", "123456")
//                        .param("passwordConfirm", "123456")
//                        .param("role", String.valueOf(Authority.USER)))
//                .andExpectAll(status().isOk(),
////                        redirectedUrl("redirect:/account/success"))
//                .andExpect(status().is3xxRedirection())
//                .andDo(log());
//    }
}
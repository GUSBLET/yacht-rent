package com.yachtrent.main.account;

import com.yachtrent.main.dto.account.SignUpViewModel;
import com.yachtrent.main.role.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
* јннотаци€ @WebMvcTest отдел€ет конкретный контроллер от насто€щего сервера
* и пердостовл€ет фиктвный сервер(если говорить проще то имитирует реальный сервер)
*
* ссылка на док:
* https://docs.spring.io/spring-framework/reference/testing/webtestclient.html
* */
@WebMvcTest(AccountController.class)
//анотаци€ делает инджект(создает экземпл€р) без фильров (они мешают делает тест)
@AutoConfigureMockMvc(addFilters = false)
class TestLoginAndRegister {

    @Autowired
    private MockMvc mockMvc;

    /*
    * јннотаци€ @MockBean имитирует поведение св€зних объектов
    * */
    @MockBean
    private IAccountService accountService;

    @MockBean
    private AccountController.SignUp signUp;

    @MockBean
    private SignUpViewModel signUpViewModel;

    @Test
    void testLoginPage() throws Exception {
        this.mockMvc.perform(get("/account/login-page"))
                .andExpectAll(
                        status().isOk(),
                        model().attribute("title", "Login"),
                        model().attribute("content", "account/login-page"),
                        model().attribute("signUpViewModel",
                                new AccountController.SignUp(null,null,null)),
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
                        model().attribute("signUpViewModel", new SignUpViewModel()),
                        view().name("layout"))
                .andDo(log());
    }

//    @Test
//    void TestSignUp() throws Exception {
//        when(accountService.signUpAsync(signUpViewModel)).getMock();
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
package fontys.sem3.school.controller;

import fontys.sem3.school.business.LoginUseCase;
import fontys.sem3.school.domain.LoginRequest;
import fontys.sem3.school.domain.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUseCase loginUseCase;

    @Test
    void login_shouldReturn201WithAccessToken_whenLoginRequestIsValid() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("user")
                .password("password")
                .build();

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken("dummy-token")
                .build();

        when(loginUseCase.login(loginRequest)).thenReturn(loginResponse);

        mockMvc.perform(post("/tokens")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "username": "user",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {
                            "accessToken": "dummy-token"
                        }
                        """));

        verify(loginUseCase).login(loginRequest);
    }

    @Test
    void login_shouldReturn400_whenLoginRequestIsInvalid() throws Exception {
        mockMvc.perform(post("/tokens")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "username": "",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // Additional verification to ensure loginUseCase.login is not called can be added if necessary.
    }
}

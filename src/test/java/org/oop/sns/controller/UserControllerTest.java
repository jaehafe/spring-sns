package org.oop.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.oop.sns.controller.request.UserJoinRequest;
import org.oop.sns.controller.request.UserLoginRequest;
import org.oop.sns.exception.SnsApplicationException;
import org.oop.sns.model.User;
import org.oop.sns.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void SignUp() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void SignUp_already_has_username() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    public void 로그인() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.login(username, password)).thenReturn("test token");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 로그인시_회원가입이_안된_username을_입력할경우_에러반환() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void 로그인시_틀린password_입력할경우_에러반환() throws Exception {
        String username = "username";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }
}

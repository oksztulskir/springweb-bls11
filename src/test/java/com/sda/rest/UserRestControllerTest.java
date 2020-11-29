package com.sda.rest;

import com.sda.model.User;
import com.sda.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest {
    private static final long ID = 1;
    private static final String EMAIL = "myemail@gmail.com";
    private static final String LOGIN = "mylogin";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "Jan";
    private static final String LAST_NAME = "Kowalski";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnFoundUserDto() throws Exception {
        // given
        final User user = getUser();
        Mockito.when(userService.find(ID)).thenReturn(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/rest/users/{id}", ID)
                .accept(MediaType.APPLICATION_JSON);

        // when
        // then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(user.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", CoreMatchers.is(user.getLogin())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(user.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(user.getPassword())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())));
        Mockito.verify(userService).find(ID);
    }

    @Test
    void shouldDeleteUser() throws Exception {
        // given
        Mockito.doNothing().when(userService).delete(ID);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/rest/users/{id}", ID)
                .accept(MediaType.APPLICATION_JSON);

        // when
        // then
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isAccepted());
        Mockito.verify(userService).delete(ID);
    }

    private static User getUser() {
        User user = new User();
        user.setId(ID);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);

        return user;
    }
}

package com.example.assignment2.user;

import com.example.assignment2.BaseControllerTest;
import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.assignment2.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {

        List<UserDTO> users = TestCreationFactory.listOf(UserDTO.class);
        when(userService.findAll()).thenReturn(users);

        ResultActions response = mockMvc.perform(get(USERS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));
    }

    @Test
    void edit() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();

        when(userService.edit(id, user)).thenReturn(user);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, user, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void getUser() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();

        when(userService.getUser(id)).thenReturn(user);

        ResultActions result = performGetWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void changePassword() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();
        String password = "password";

        when(userService.changePassword(id,password)).thenReturn(user);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + ENTITY, password, id);
        verify(userService,times(1)).changePassword(id,password);
        result.andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();

        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(USERS + ENTITY, id);
        verify(userService,times(1)).delete(id);
        result.andExpect(status().isOk());
    }
}
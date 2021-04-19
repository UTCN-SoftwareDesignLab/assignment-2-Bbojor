package com.example.assignment2.user;

import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.user.mapper.UserMapper;
import com.example.assignment2.user.model.User;
import com.example.assignment2.user.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService ;

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.saveAll(users);

        List<UserDTO> all = userService.findAll();
        for(int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getEmail(), all.get(i).getEmail());
            assertEquals(users.get(i).getUsername(), all.get(i).getUsername());
            assertEquals(users.get(i).getRoles(), all.get(i).getRoles());
        }
    }
}
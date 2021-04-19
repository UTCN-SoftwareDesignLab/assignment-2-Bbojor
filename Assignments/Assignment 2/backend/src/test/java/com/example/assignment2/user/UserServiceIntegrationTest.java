package com.example.assignment2.user;

import com.example.assignment2.TestCreationFactory;
import com.example.assignment2.user.model.User;
import com.example.assignment2.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() { userRepository.deleteAll();}

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

    @Test
    void getUSer() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);

        UserDTO foundUser = userService.getUser(user.getId());

        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getRoles(), foundUser.getRoles());
    }

    @Test
    public void edit() {
        User user = TestCreationFactory.newUser();
        user = userRepository.save(user);

        UserDTO newUser = TestCreationFactory.newUserDTO();
        userService.edit(user.getId(), newUser);

        List<UserDTO> allUsers = userService.findAll();
        assertEquals(1, allUsers.size());
        assertEquals(newUser.getUsername(), allUsers.get(0).getUsername());
        assertEquals(newUser.getEmail(), allUsers.get(0).getEmail());
        assertEquals(newUser.getRoles(), allUsers.get(0).getRoles());
    }

    @Test
    public void delete() {
        List<User> users = TestCreationFactory.listOf(User.class);
        int originalSize = users.size();
        userRepository.saveAll(users);

        User userToDelete = userRepository.findByUsername(users.get(0).getUsername())
                .orElseThrow(() -> new RuntimeException("Cannot find user "));

        userService.delete(userToDelete.getId());

        List<UserDTO> foundUsers = userService.findAll();
        assertEquals(originalSize - 1, foundUsers.size());
    }

    @Test
    public void changePassword() {
        User user = TestCreationFactory.newUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        String newPassword = TestCreationFactory.randomString();
        UserDTO changedUser = userService.changePassword(user.getId(), newPassword);

        User foundUser =  userRepository.findByUsername(changedUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Cannot find user "));

        assertTrue(passwordEncoder.matches(newPassword, foundUser.getPassword()));
    }
}
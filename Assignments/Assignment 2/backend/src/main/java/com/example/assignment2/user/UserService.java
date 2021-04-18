package com.example.assignment2.user;

import com.example.assignment2.user.dto.UserDTO;
import com.example.assignment2.user.mapper.UserMapper;
import com.example.assignment2.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO edit(Long id, UserDTO user) {
        User actualUser = findById(id);
        actualUser.setUsername(user.getUsername());
        actualUser.setEmail(user.getEmail());
        actualUser.setRoles(user.getRoles());
        return null;
    }

    public UserDTO getUser(Long id) {
        return userMapper.toDto(
                userRepository.getOne(id)
        );
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO changePassword(Long id, String newPassword) {
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userMapper.toDto(userRepository.save(user));
    }
}

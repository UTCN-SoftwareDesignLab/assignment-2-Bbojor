package com.example.assignment2.user;

import com.example.assignment2.user.dto.PasswordChangeRequest;
import com.example.assignment2.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.example.assignment2.UrlMapping.ENTITY;
import static com.example.assignment2.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @PutMapping(ENTITY)
    public UserDTO edit(@PathVariable Long id ,@Valid @RequestBody UserDTO user) { return userService.edit(id, user); }

    @GetMapping(ENTITY)
    public UserDTO getUser(@PathVariable Long id) { return userService.getUser(id);}

    @PatchMapping(ENTITY)
    public UserDTO changePassword(@PathVariable Long id, @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return userService.changePassword(id, passwordChangeRequest.getPassword());
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) { userService.delete(id);}

    //Catch exceptions thrown by validators in controller methods and put the errors in a list
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return errors;
    }

}

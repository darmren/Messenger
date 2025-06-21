package messenger.userservice.controller;

import lombok.RequiredArgsConstructor;
import messenger.userservice.exception.UserNotFoundException;
import messenger.userservice.model.User;
import messenger.userservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    private final UserRepository userRepository;
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/by-id/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @GetMapping("/by-username/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}

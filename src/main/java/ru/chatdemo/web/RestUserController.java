package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.chatdemo.model.User;
import ru.chatdemo.model.UserEvent;
import ru.chatdemo.repository.UserRepository;
import ru.chatdemo.to.UserListTo;
import ru.chatdemo.util.UserValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class RestUserController {

    private final UserRepository repository;

    private final UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @GetMapping
    public UserListTo getAll(HttpSession session) {
        return repository.getUserListTo(repository.getBySession(session));
    }

    @GetMapping("/update")
    public List<UserEvent> getUserEvents(Long maxId, HttpSession session) {
        return repository.getEvents(maxId);
    }

    @GetMapping("/profile")
    public User getProfile(HttpSession session) {
        log.info("Get logged user for session with id {}", session.getId());
        return repository.getBySession(session);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid User user, HttpSession session) {
        log.info("Enter chat system with user {}", user);
        User created = repository.add(session, user);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/logout")
    public void delete(HttpSession session) {
        log.info("Invalidate and logout user {}", repository.getBySession(session));
        session.invalidate();
    }
}

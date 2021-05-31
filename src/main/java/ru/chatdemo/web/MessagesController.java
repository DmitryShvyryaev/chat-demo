package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.chatdemo.model.Message;
import ru.chatdemo.model.User;
import ru.chatdemo.repository.MessageRepository;
import ru.chatdemo.service.MessageService;
import ru.chatdemo.to.MessageTo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class MessagesController {

    private final MessageService service;
    private final User loggedUser;

    @GetMapping
    public List<MessageTo> getAll() {
        log.info("Get all messages");
        return service.getAll();
    }

    @GetMapping("/my-name")
    public String getUsername() {
        log.info("Get my name");
        return loggedUser.getName();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(Message message) {
        log.info("Create new Message {} of User {}", message, loggedUser);
        message.setUsername(loggedUser.getName());
        message.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        service.insert(message);
    }
}

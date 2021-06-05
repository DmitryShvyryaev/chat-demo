package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.chatdemo.model.Message;
import ru.chatdemo.model.User;
import ru.chatdemo.service.MessageService;
import ru.chatdemo.to.MessageTo;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService service;

    @GetMapping
    public List<MessageTo> getAll() {
        log.info("Get all messages");
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(Message message, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        log.info("Create new Message {} of User {}", message, user);
        message.setUsername(user.getName());
        message.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        service.insert(message);
    }
}
package ru.chatdemo.service;

import org.springframework.stereotype.Service;
import ru.chatdemo.model.Message;
import ru.chatdemo.repository.MessageRepository;
import ru.chatdemo.to.MessageTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public List<MessageTo> getAll() {
        return repository.getAll().stream()
                .map(this::getTos)
                .collect(Collectors.toList());
    }

    public void insert(Message message) {
        repository.insert(message);
    }

    private MessageTo getTos(Message message) {
        LocalDate today = LocalDate.now();
        return new MessageTo(message.getUsername(), convertDateTime(message.getDateTime(), today), message.getContent());
    }

    private String convertDateTime(LocalDateTime startDateTime, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDate);
        String date = days == 0 ? "Сегодня" : startDateTime.toLocalDate().toString();
        return date + " " + startDateTime.toLocalTime().toString();
    }
}

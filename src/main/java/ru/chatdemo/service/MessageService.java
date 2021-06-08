package ru.chatdemo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chatdemo.model.Message;
import ru.chatdemo.repository.MessageRepository;
import ru.chatdemo.to.MessageTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository repository;

    public List<MessageTo> getAll() {
        return getFilteredSortedList(message -> true);
    }

    public List<MessageTo> getLastPart() {
        long limit = repository.getCounter() - 200;
        return getFilteredSortedList(message -> message.getId() >= limit);
    }

    public List<MessageTo> getAfterLimit(long limit) {
        return getFilteredSortedList(message -> message.getId() > limit);
    }

    public void save(Message message) {
        repository.save(message);
    }

    private MessageTo getTo(Message message, LocalDate today) {
        return new MessageTo(message.getId(), message.getUsername(), convertDateTime(message.getDateTime(), today), message.getContent());
    }

    private String convertDateTime(LocalDateTime startDateTime, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDate);
        String date = days == 0 ? "Сегодня" : startDateTime.toLocalDate().toString();
        return date + " " + startDateTime.toLocalTime().toString();
    }

    private List<MessageTo> getFilteredSortedList(Predicate<Message> filter) {
        LocalDate today = LocalDate.now();
        return repository.getAll().stream()
                .filter(filter)
                .map(message -> getTo(message, today))
                .sorted(Comparator.comparing(MessageTo::getId))
                .collect(Collectors.toList());
    }
}

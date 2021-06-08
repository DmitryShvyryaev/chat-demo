package ru.chatdemo.repository;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.chatdemo.model.Message;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class MessageRepository {

    private final Map<Long, Message> repository = new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong(10000);

    public Collection<Message> getAll() {
        return repository.values();
    }

    public Message save(Message message) {
        message.setId(counter.getAndIncrement());
        repository.put(message.getId(), message);
        return message;
    }

    public Long getCounter() {
        return counter.get();
    }
}

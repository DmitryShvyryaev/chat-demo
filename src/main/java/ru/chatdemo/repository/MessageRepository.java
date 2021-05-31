package ru.chatdemo.repository;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.chatdemo.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessageRepository {

    private List<Message> repository = Collections.synchronizedList(new ArrayList<Message>());

    public List<Message> getAll() {
        return repository;
    }

    public void insert(Message message) {
        repository.add(message);
        resize();
    }

    private synchronized void resize() {
        if (repository.size() > 150) {
            repository = repository.stream().skip(50)
                    .collect(Collectors.toList());
        }
    }
}

package ru.chatdemo;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.chatdemo.model.Message;
import ru.chatdemo.repository.MessageRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@AllArgsConstructor
public class ChatDemoApplication implements ApplicationRunner {

    private final MessageRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ChatDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        repository.insert(new Message("Dmitry", LocalDate.now().atTime(11,0), "Heelo brothers"));
        repository.insert(new Message("Alexy", LocalDate.now().atTime(9,36), "Hey Im OK"));
        repository.insert(new Message("Dmitry", LocalDate.now().minusDays(1).atTime(11,0), "And you"));
        repository.insert(new Message("Alexy", LocalDate.now().minusDays(3).atTime(11,0),"Heelo brothers"));
    }
}

package ru.chatdemo;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.chatdemo.model.Message;
import ru.chatdemo.repository.MessageRepository;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class ChatDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatDemoApplication.class, args);
    }
}

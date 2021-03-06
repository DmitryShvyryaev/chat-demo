package ru.chatdemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private String username;
    private LocalDateTime dateTime;
    private String content;

    public Message() {
    }

    public Message(String username, LocalDateTime dateTime, String content) {
        this.username = username;
        this.dateTime = dateTime;
        this.content = content;
    }
}

package ru.chatdemo.to;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageTo {
    private Long id;
    private String username;
    private String dateTime;
    private String content;
}

package ru.chatdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEvent {
    private Long id;
    private User user;
    private UserAction action;

    @Override
    public String toString() {
        return "UserEvent{" +
                "id=" + id +
                ", user=" + user +
                ", action=" + action +
                '}';
    }
}

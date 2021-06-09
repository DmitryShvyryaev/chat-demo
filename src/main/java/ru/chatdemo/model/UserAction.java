package ru.chatdemo.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserAction {
    CREATE("create"),
    DELETE("delete");

    private String action;

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "UserAction{" +
                "action='" + action + '\'' +
                '}';
    }
}

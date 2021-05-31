package ru.chatdemo.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@SessionScope
@Component
public class User {
    private String name;
    private String status;

    public void destroy() {
        this.name = "";
        this.status = "";
    }
}

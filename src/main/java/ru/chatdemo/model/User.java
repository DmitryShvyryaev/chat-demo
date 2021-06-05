package ru.chatdemo.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@SessionScope
@Component
public class User {

    @NotBlank
    @Size(min = 5, max = 15)
    private String name;

    @NotBlank
    @Size(min = 1, max = 30)
    private String status;

    public void destroy() {
        this.name = "";
        this.status = "";
    }
}

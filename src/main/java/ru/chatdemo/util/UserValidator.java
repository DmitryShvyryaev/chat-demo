package ru.chatdemo.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chatdemo.model.User;
import ru.chatdemo.web.SessionListener;

@Component
public class UserValidator implements Validator {

    private final SessionListener sessionListener;

    public UserValidator(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user= (User) o;
        User duplicate = sessionListener.getAllUsers().stream()
                .filter(u -> u.getName().equalsIgnoreCase(user.getName()))
                .findAny().orElse(null);
        if (duplicate != null) {
            errors.rejectValue("name", "exception.user.duplicateName");
        }
    }
}

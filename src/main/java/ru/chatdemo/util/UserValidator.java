package ru.chatdemo.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chatdemo.model.User;
import ru.chatdemo.repository.UserRepository;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user= (User) o;
        User duplicate = userRepository.getAllUsers().stream()
                .filter(u -> u.getName().equalsIgnoreCase(user.getName()))
                .findAny().orElse(null);
        if (duplicate != null) {
            errors.rejectValue("name", "exception.user.duplicateName");
        }
    }
}

package ru.chatdemo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chatdemo.exception.NotFoundException;
import ru.chatdemo.model.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository implements HttpSessionListener {

    private final Map<String, User> activeUsers = new ConcurrentHashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeUsers.remove(se.getSession().getId());
    }

    public User add(HttpSession session, User user) {
        session.setAttribute("loggedUser", user);
        return activeUsers.put(session.getId(), user);
    }

    public Collection<User> getAllUsers() {
        return activeUsers.values();
    }

    public User getBySession(HttpSession session) {
        return activeUsers.computeIfAbsent(session.getId(), s -> {throw new NotFoundException("Not found user in session with id " + session.getId());});
    }
}

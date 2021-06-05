package ru.chatdemo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chatdemo.model.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionListener implements HttpSessionListener {

    private final Map<String, User> activeUsers = new ConcurrentHashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeUsers.remove(se.getSession().getId());
    }

    public void add(HttpSession session, User user) {
        activeUsers.put(session.getId(), user);
    }

    public Collection<User> getAllUsers() {
        return activeUsers.values();
    }
}

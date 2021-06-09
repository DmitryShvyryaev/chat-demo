package ru.chatdemo.repository;

import org.springframework.stereotype.Component;
import ru.chatdemo.exception.NotFoundException;
import ru.chatdemo.model.User;
import ru.chatdemo.model.UserAction;
import ru.chatdemo.model.UserEvent;
import ru.chatdemo.to.UserListTo;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class UserRepository implements HttpSessionListener {

    private final Map<String, User> activeUsers = new ConcurrentHashMap<>();
    private final Queue<UserEvent> userEvents = new ConcurrentLinkedQueue<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        User deleted = activeUsers.remove(se.getSession().getId());
        addUserEvent(deleted, UserAction.DELETE);
    }

    public User add(HttpSession session, User user) {
        session.setAttribute("loggedUser", user);
        addUserEvent(user, UserAction.CREATE);
        return activeUsers.put(session.getId(), user);
    }

    public Collection<User> getAllUsers() {
        return activeUsers.values();
    }

    public UserListTo getUserListTo(User user) {
        List<User> users = getAllUsers().stream()
                .filter(u -> !u.equals(user))
                .collect(Collectors.toList());
        return new UserListTo(counter.get() - 1, users);
    }

    public User getBySession(HttpSession session) {
        return activeUsers.computeIfAbsent(session.getId(), s -> {
            throw new NotFoundException("Not found user in session with id " + session.getId());
        });
    }

    public List<UserEvent> getEvents(long maxId) {
        return userEvents.stream()
                .filter(event -> event.getId() > maxId)
                .collect(Collectors.toList());
    }

    private void addUserEvent(User user, UserAction action) {
        UserEvent event = new UserEvent(counter.getAndIncrement(), user, action);
        userEvents.offer(event);
        resizeEvents();
    }

    private synchronized void resizeEvents() {
        if (userEvents.size() > 100)
            userEvents.poll();
    }
}

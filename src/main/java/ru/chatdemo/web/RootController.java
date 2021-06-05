package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import ru.chatdemo.model.User;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@Slf4j
public class RootController {

    @GetMapping("/")
    public String root() {
        log.info("get root page");
        return "redirect:messages";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        log.info("Get login page");
        if (isAuthorized(session)) {
            return "redirect:messages";
        }
        return "login";
    }

    @GetMapping("/messages")
    public String getMessages(HttpSession session) {
        log.info("Get messages page");
        if (!isAuthorized(session))
            return "redirect:login";
        return "messages";
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        return user != null && StringUtils.hasLength(user.getName());
    }
}

package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "redirect:messages";
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        if (isAuthorized(session)) {
            return "redirect:messages";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/messages")
    public String getMessages(HttpSession session) {
        if (!isAuthorized(session))
            return "redirect:login";
        return "messages";
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        return user != null && StringUtils.hasLength(user.getName());
    }
}
